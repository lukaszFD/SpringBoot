import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CsvReaderService {

    @Autowired
    private Charset charset;

    public <T> List<T> readCsvFile(File file, Class<T> clazz, Integer skipRowsBeforeHeader, List<Integer> skipRecords) {
        List<T> parsedData = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(file);
             InputStreamReader isr = new InputStreamReader(fis, charset)) {

            CSVFormat csvFormat = CSVFormat.DEFAULT;

            try (CSVParser csvParser = csvFormat.parse(isr)) {

                // Pomijanie wierszy przed nagłówkiem
                if (skipRowsBeforeHeader != null && skipRowsBeforeHeader > 0) {
                    for (int i = 0; i < skipRowsBeforeHeader; i++) {
                        csvParser.iterator().next();
                    }
                }

                List<String> headerNames = getHeaderNames(csvParser);

                for (CSVRecord csvRecord : csvParser) {
                    int recordNumber = csvRecord.getRecordNumber();
                    if (skipRecords == null || skipRecords.isEmpty() || !skipRecords.contains(recordNumber)) {
                        T myObject = parseCsvRecord(csvRecord, clazz, headerNames);
                        parsedData.add(myObject);
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return parsedData;
    }

    private List<String> getHeaderNames(CSVParser csvParser) {
        Map<String, Integer> headerMap = csvParser.getHeaderMap();
        return (headerMap != null) ? new ArrayList<>(headerMap.keySet()) : new ArrayList<>();
    }

    private <T> T parseCsvRecord(CSVRecord csvRecord, Class<T> clazz, List<String> headerNames) {
        try {
            T instance = clazz.getDeclaredConstructor().newInstance();

            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);

                Annotation annotation = getAnnotationForField(field);
                if (annotation != null) {
                    if (annotation instanceof CsvBindByPosition) {
                        CsvBindByPosition bindByPosition = (CsvBindByPosition) annotation;
                        int columnPosition = bindByPosition.position();
                        String cellValue = csvRecord.get(columnPosition);
                        field.set(instance, cellValue);
                    } else if (annotation instanceof CsvBindByName) {
                        CsvBindByName bindByName = (CsvBindByName) annotation;
                        String columnName = bindByName.column();
                        int columnIndex = headerNames.indexOf(columnName);
                        String cellValue = csvRecord.get(columnIndex);
                        field.set(instance, cellValue);
                    }
                }
            }

            return instance;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private Annotation getAnnotationForField(Field field) {
        Annotation[] annotations = field.getAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation instanceof CsvBindByPosition || annotation instanceof CsvBindByName) {
                return annotation;
            }
        }
        return null;
    }
}
