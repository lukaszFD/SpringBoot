import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.bean.MappingStrategy;
import com.opencsv.bean.ColumnPositionMappingStrategy;
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
                    .build()
                    .parse();
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
        return null;
    }

    private <T> MappingStrategy<T> createMappingStrategy(Class<T> clazz) {
        if (clazz.isAnnotationPresent(com.opencsv.bean.CsvBindByName.class)) {
            HeaderColumnNameMappingStrategy<T> mappingStrategy = new HeaderColumnNameMappingStrategy<>();
            mappingStrategy.setType(clazz);
            return mappingStrategy;
        } else {
            // Jeśli brak adnotacji, użyj strategii opartej na kolejności
            ColumnPositionMappingStrategy<T> mappingStrategy = new ColumnPositionMappingStrategy<>();
            mappingStrategy.setType(clazz);
            mappingStrategy.setColumnMapping(createColumnMapping(clazz));
            return mappingStrategy;
        }
    }

    private <T> String[] createColumnMapping(Class<T> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        int validFieldCount = 0;

        for (Field field : fields) {
            if (!field.isAnnotationPresent(CsvBindByName.class) || 
                field.isAnnotationPresent(javax.persistence.GeneratedValue.class)) {
                continue;  // Pomijamy pola bez adnotacji @CsvBindByName lub generowane automatycznie
            }
            validFieldCount++;
        }

        String[] columnMapping = new String[validFieldCount];
        int index = 0;

        for (Field field : fields) {
            if (!field.isAnnotationPresent(CsvBindByName.class) || 
                field.isAnnotationPresent(javax.persistence.GeneratedValue.class)) {
                continue;  // Pomijamy pola bez adnotacji @CsvBindByName lub generowane automatycznie
            }

            CsvBindByName annotation = field.getAnnotation(CsvBindByName.class);
            columnMapping[index++] = annotation.column();
        }

        return columnMapping;
    }
}
