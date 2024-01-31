import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.bean.MappingStrategy;
import com.opencsv.exceptions.CsvException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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
        HeaderColumnNameMappingStrategy<T> mappingStrategy = new HeaderColumnNameMappingStrategy<>();
        mappingStrategy.setType(clazz);

        // Jeśli klasa encji posiada adnotację @CsvBindByName, użyj strategii opartej na nazwach nagłówków
        if (clazz.isAnnotationPresent(com.opencsv.bean.CsvBindByName.class)) {
            mappingStrategy.setColumnMapping(new String[]{"id", "name", "otherColumn", "..."});
        } else {
            // Jeśli brak adnotacji, użyj strategii opartej na kolejności
            mappingStrategy.setColumnOrderOnWrite(new MappingStrategy.ColumnPositionMappingStrategy());
        }

        return mappingStrategy;
    }
}
