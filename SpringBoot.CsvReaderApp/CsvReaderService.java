import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CsvReaderService {

    @Value("${csv.skipRows}")
    private List<Integer> skipRows;

    public <T> List<T> readCsvFile(File file, Class<T> clazz) {
        try (FileReader reader = new FileReader(file)) {
            CsvToBeanBuilder<T> csvToBeanBuilder = new CsvToBeanBuilder<>(reader)
                    .withType(clazz);

            if (!skipRows.isEmpty()) {
                // Pomijaj wiersze przy użyciu .withSkipLines
                csvToBeanBuilder.withSkipLines(skipRows.get(0));
            }

            List<T> parsedData = csvToBeanBuilder.build().parse();

            if (skipRows.size() > 1) {
                // Usuń dodatkowe wiersze z listy parsedData
                List<T> rowsToRemove = new ArrayList<>();
                for (int i = 1; i < skipRows.size(); i++) {
                    int rowNumber = skipRows.get(i);
                    if (rowNumber < parsedData.size()) {
                        rowsToRemove.add(parsedData.get(rowNumber));
                    }
                }
                parsedData.removeAll(rowsToRemove);
            }

            // Sprawdź, czy w pierwszym wierszu występują dodatkowe spacje
            if (containsExtraSpaces(parsedData.get(0))) {
                // Jeśli tak, zastosuj do całej listy
                trimSpacesFromData(parsedData);
            }

            return parsedData;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private <T> boolean containsExtraSpaces(T row) {
        // Sprawdź czy w pierwszym wierszu występują dodatkowe spacje
        for (java.lang.reflect.Field field : row.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                Object value = field.get(row);
                if (value instanceof String && ((String) value).trim().length() != ((String) value).length()) {
                    return true;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private <T> void trimSpacesFromData(List<T> data) {
        for (T row : data) {
            // Użyj refleksji, aby uzyskać dostęp do pól wiersza
            for (java.lang.reflect.Field field : row.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                try {
                    Object value = field.get(row);
                    if (value instanceof String) {
                        // Jeśli wartość jest typu String, usuń niepotrzebne spacje
                        field.set(row, ((String) value).trim());
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
