import com.opencsv.bean.*;
import com.opencsv.exceptions.CsvException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

public class CsvReaderService {
    public <T> List<T> readCsvFile(File file, Class<T> clazz) {
        try (FileReader reader = new FileReader(file)) {
            MappingStrategy<T> mappingStrategy = createMappingStrategy(clazz);

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

    private <T> MappingStrategy<T> createMappingStrategy(Class<T> clazz) {
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
