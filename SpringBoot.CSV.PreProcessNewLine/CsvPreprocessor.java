import java.io.*;

public class CsvPreprocessor {

    public static InputStreamReader preprocessCsv(InputStreamReader inputStreamReader) throws IOException {
        StringBuilder result = new StringBuilder();
        
        try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Usuwanie samotnych CR i LF
                line = line.replaceAll("\r(?!\n)", "").replaceAll("(?<!\r)\n", "");
                result.append(line).append(System.lineSeparator());
            }
        }

        // Konwersja przetworzonego tekstu na InputStreamReader
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(result.toString().getBytes());
        return new InputStreamReader(byteArrayInputStream);
    }

    public static void main(String[] args) throws IOException {
        String filePath = "input.csv";
        
        try (InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(filePath))) {
            InputStreamReader processedReader = preprocessCsv(inputStreamReader);

            // Odczytywanie i zapisywanie przetworzonego pliku z powrotem do oryginalnego pliku
            try (BufferedReader reader = new BufferedReader(processedReader);
                 BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    writer.write(line);
                    writer.newLine();
                }
            }
        }
    }
}
