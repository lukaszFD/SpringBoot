import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class GenericService<T> {

    private static final Logger logger = LoggerFactory.getLogger(GenericService.class);

    private final JpaRepository<T, Long> repository;
    private final BatchSizeStatisticsRepository statisticsRepository;

    private List<BatchSizeStatistics> batchSizeStatistics;

    @Autowired
    public GenericService(JpaRepository<T, Long> repository, BatchSizeStatisticsRepository statisticsRepository) {
        this.repository = repository;
        this.statisticsRepository = statisticsRepository;
        this.batchSizeStatistics = new ArrayList<>();
    }

    @Transactional
    public void saveEntitiesInParallel(List<T> entities) {
        Instant start = Instant.now();

        int batchSize = determineOptimalBatchSize(getTableName());
        int threads = Runtime.getRuntime().availableProcessors();

        ExecutorService executorService = Executors.newFixedThreadPool(threads);
        List<Future<?>> futures = new ArrayList<>();
        List<String> errorMessages = new CopyOnWriteArrayList<>();

        for (int i = 0; i < entities.size(); i += batchSize) {
            int endIndex = Math.min(i + batchSize, entities.size());

            List<T> batch = entities.subList(i, endIndex);

            Future<?> future = executorService.submit(() -> {
                try {
                    saveBatch(batch, getTableName(), threads);
                } catch (Exception e) {
                    String errorMessage = "Error saving batch: " + e.getMessage();
                    errorMessages.add(errorMessage);
                    executorService.shutdownNow();
                }
            });
            futures.add(future);
        }

        // Sprawdzanie czy któryś z wątków zakończył się niepowodzeniem
        for (Future<?> future : futures) {
            try {
                future.get(); // Blokuje do zakończenia taska, lub wyrzuca ExecutionException
            } catch (Exception e) {
                String errorMessage = "Error in parallel execution: " + e.getMessage();
                errorMessages.add(errorMessage);
                executorService.shutdownNow();
                break;
            }
        }

        executorService.shutdown();
        while (!executorService.isTerminated()) {
            // Czekaj, aż wszystkie wątki zakończą pracę
        }

        // Logowanie wszystkich zebranych błędów
        if (!errorMessages.isEmpty()) {
            for (String errorMessage : errorMessages) {
                logger.error(errorMessage);
            }
        }

        Instant end = Instant.now();
        Duration executionTime = Duration.between(start, end);

        updateBatchSizeStatistics(batchSize, entities.size(), executionTime, getTableName(), threads);
    }

    @Transactional
    public void saveBatch(List<T> entities, String tableName, int numberOfThreads) {
        // Zapisz wsad do odpowiedniej tabeli
        // ...

        for (T entity : entities) {
            try {
                repository.save(entity);
            } catch (Exception e) {
                String errorMessage = "Error saving entity: " + e.getMessage();
                throw new RuntimeException(errorMessage, e); // Rzucenie wyjątku aby go obsłużyć w wątku nadrzędnym
            }
        }
    }

    private int determineOptimalBatchSize(String tableName) {
        // Pobierz dane statystyczne dla danej tabeli
        List<BatchSizeStatistics> tableStatistics = getStatisticsForTable(tableName);

        // Jeśli brak danych statystycznych, zwróć domyślny rozmiar wsadu
        if (tableStatistics.isEmpty()) {
            return 1000;
        }

        // Posortuj dane statystyczne według czasu wykonania
        tableStatistics.sort(Comparator.comparingLong(stat -> stat.getExecutionTime().toMillis()));

        // Wybierz rozmiar wsadu na podstawie przykładowej logiki
        int medianIndex = tableStatistics.size() / 2;
        int medianBatchSize = tableStatistics.get(medianIndex).getBatchSize();
        int calculatedBatchSize = Math.max(100, Math.min(10000, medianBatchSize * 10 / numberOfThreads));

        return calculatedBatchSize;
    }

    private List<BatchSizeStatistics> getStatisticsForTable(String tableName) {
        return statisticsRepository.findByTableName(tableName);
    }

    private void updateBatchSizeStatistics(int batchSize, int dataSize, Duration executionTime, String tableName, int numberOfThreads) {
        BatchSizeStatistics statistics = new BatchSizeStatistics(batchSize, dataSize, executionTime, tableName, numberOfThreads);
        statisticsRepository.save(statistics);
        batchSizeStatistics.add(statistics);
        // Tutaj możesz przeprowadzić bardziej zaawansowaną analizę danych historycznych
        // i dostosować strategię wyboru rozmiaru wsadu w zależności od wcześniejszych doświadczeń
    }

    private String getTableName() {
        Class<?> entityClass = repository.getDomainType();
        return entityClass.getAnnotation(Table.class).name();
    }
}