import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class DataLoaderService {

    public List<DataEntry> loadDataFromFile(String filePath) throws IOException {
        List<DataEntry> dataList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            // Pominięcie pierwszych 5 wierszy
            for (int i = 0; i < 5; i++) {
                br.readLine();
            }

            String line;
            while ((line = br.readLine()) != null) {
                String[] columns = line.split("\\|", 22);

                // Tworzenie instancji DataEntry
                DataEntry dataEntry = new DataEntry();
                for (int i = 0; i < 21; i++) {
                    // Przypisanie wartości do odpowiednich kolumn
                    setColumnValue(dataEntry, i, columns[i]);
                }

                // Jeśli są dodatkowe dane (więcej niż 21 kolumna), traktuj jako jedna kolumna rozdzielona przecinkiem
                if (columns.length > 21) {
                    dataEntry.setAdditionalColumn(columns[21]);
                }

                // Dodanie do listy
                dataList.add(dataEntry);
            }
        }

        return dataList;
    }

    private void setColumnValue(DataEntry dataEntry, int columnIndex, String columnValue) {
        // Przykład: ustawienie wartości dla kolumny
        switch (columnIndex) {
            case 0:
                dataEntry.setColumn1(columnValue);
                break;
            case 1:
                dataEntry.setColumn2(columnValue);
                break;
            // ... ustawienia dla kolejnych kolumn
        }
    }
}
