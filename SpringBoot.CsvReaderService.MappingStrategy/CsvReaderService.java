import com.opencsv.bean.*;
import com.opencsv.exceptions.CsvException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.regex.Pattern;

public class CsvReaderService {
    public <T> List<T> readCsvFile(File file, Class<T> clazz) {
        try (FileReader reader = new FileReader(file)) {
            MappingStrategy<T> mappingStrategy = createMappingStrategy(clazz, file);

            return new CsvToBeanBuilder<T>(reader)
                    .withMappingStrategy(mappingStrategy)
                    .withExceptionHandler(new CustomExceptionHandler<>(clazz))
                    .build()
                    .parse();
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
        return null;
    }

    private <T> MappingStrategy<T> createMappingStrategy(Class<T> clazz, File file) {
        if (shouldUseRFC4180MappingStrategy(file)) {
            return new RFC4180MappingStrategy<>(clazz);
        }

        // Jeśli nie używamy RFC4180MappingStrategy, sprawdź inne strategie
        if (hasCsvBindByNameFields(clazz)) {
            // Użyj strategii opartej na nagłówku, jeśli klasa ma pola z adnotacją @CsvBindByName
            HeaderColumnNameMappingStrategy<T> mappingStrategy = new HeaderColumnNameMappingStrategy<>();
            mappingStrategy.setType(clazz);
            return mappingStrategy;
        } else {
            // Użyj strategii opartej na kolejności, jeśli brak pól z adnotacją @CsvBindByName
            ColumnPositionMappingStrategy<T> mappingStrategy = new ColumnPositionMappingStrategy<>();
            mappingStrategy.setType(clazz);
            return mappingStrategy;
        }
    }

    private <T> boolean shouldUseRFC4180MappingStrategy(File file) {
        try (FileReader fileReader = new FileReader(file)) {
            char separator = detectSeparator(fileReader);
            boolean containsQuotes = detectQuotes(fileReader);
            return separator == ',' && containsQuotes;
        } catch (IOException e) {
            e.printStackTrace();
            return false; // Domyślnie nie używaj RFC4180MappingStrategy w przypadku błędu
        }
    }

    private char detectSeparator(FileReader fileReader) throws IOException {
        char separator = ',';
        String firstLine = new CSVReaderBuilder(fileReader).readNext()[0]; // Odczytaj pierwszą linię
        if (Pattern.compile("\\t").matcher(firstLine).find()) {
            separator = '\t'; // Jeśli w pierwszej linii znajduje się tabulator, ustaw separator na tabulator
        }
        return separator;
    }

    private boolean detectQuotes(FileReader fileReader) throws IOException {
        String firstLine = new CSVReaderBuilder(fileReader).readNext()[0];
        return firstLine.contains("\"");
    }

    private <T> boolean hasCsvBindByNameFields(Class<T> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(CsvBindByName.class)) {
                return true;
            }
        }
        return false;
    }
}
