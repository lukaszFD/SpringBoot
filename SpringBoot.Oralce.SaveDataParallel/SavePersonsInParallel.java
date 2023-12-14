import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Transactional
    public void savePersonsInParallel(List<Person> persons) {
        int batchSize = 1000;
        int threads = Runtime.getRuntime().availableProcessors(); // Liczba dostępnych procesorów

        ExecutorService executorService = Executors.newFixedThreadPool(threads);

        for (int i = 0; i < persons.size(); i += batchSize) {
            int endIndex = Math.min(i + batchSize, persons.size());

            List<Person> batch = persons.subList(i, endIndex);

            executorService.submit(() -> {
                saveBatch(batch);
            });
        }

        executorService.shutdown();
        while (!executorService.isTerminated()) {
            // Czekaj, aż wszystkie wątki zakończą pracę
        }
    }

    @Transactional
    public void saveBatch(List<Person> persons) {
        for (Person person : persons) {
            personRepository.save(person);
        }
    }
}
