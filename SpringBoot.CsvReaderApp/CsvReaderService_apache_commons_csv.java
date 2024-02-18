import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.annotations.CsvBindByName;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CsvReaderService {

    public <T> List<T> readCsvFile(File file, Class<T> clazz, boolean hasQuotes) {
        List<T> parsedData = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(file);
             InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_16LE)) {

            CSVFormat csvFormat = hasQuotes ? CSVFormat.DEFAULT.withQuote('"') : CSVFormat.DEFAULT;

            try (CSVParser csvParser = csvFormat.parse(isr)) {
                for (CSVRecord csvRecord : csvParser) {
                    T myObject = parseCsvRecord(csvRecord, clazz);
                    parsedData.add(myObject);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return parsedData;
    }

    private <T> T parseCsvRecord(CSVRecord csvRecord, Class<T> clazz) {
        try {
            T instance = clazz.getDeclaredConstructor().newInstance();

            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);

                CsvBindByName annotation = field.getAnnotation(CsvBindByName.class);
                if (annotation != null) {
                    String columnName = annotation.column();
                    String cellValue = csvRecord.get(columnName);
                    // Przypisz wartość do pola
                    field.set(instance, cellValue);
                }
            }

            return instance;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
