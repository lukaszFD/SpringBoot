import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@Service
public class CsvReaderService {

    public <T> List<T> readCsvFile(File file, Class<T> clazz) {
        try (FileReader reader = new FileReader(file)) {
            return new CsvToBeanBuilder<T>(reader)
                    .withType(clazz)
                    .build()
                    .parse();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
