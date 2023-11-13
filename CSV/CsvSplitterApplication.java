import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class CsvSplitterApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(CsvSplitterApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        if (args.length == 0) {
            System.out.println("Podaj ścieżkę do pliku CSV jako argument.");
            return;
        }

        String inputFilePath = args[0];
        splitCsvFile(inputFilePath);
    }

    private void splitCsvFile(String inputFilePath) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(inputFilePath))) {
            List<String> header = readHeader(reader);
            List<List<String>> rows = readRows(reader);

            int chunkSize = rows.size() > 1000000 ? 8 : 4;
            List<List<List<String>>> chunks = chunkList(rows, chunkSize);

            saveChunks(inputFilePath, header, chunks);

            System.out.println("Pliki zostały pomyślnie podzielone.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<String> readHeader(BufferedReader reader) throws IOException {
        String headerLine = reader.readLine();
        return parseCsvLine(headerLine);
    }

    private List<List<String>> readRows(BufferedReader reader) throws IOException {
        List<List<String>> rows = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            List<String> row = parseCsvLine(line);
            rows.add(row);
        }
        return rows;
    }

    private List<String> parseCsvLine(String line) {
        // Prosta implementacja parsowania linii CSV (możesz dostosować do konkretnych wymagań)
        String[] tokens = line.split(",");
        List<String> values = new ArrayList<>();
        for (String token : tokens) {
            values.add(token.trim());
        }
        return values;
    }

    private <T> List<List<T>> chunkList(List<T> list, int chunkSize) {
        List<List<T>> chunks = new ArrayList<>();
        for (int i = 0; i < list.size(); i += chunkSize) {
            int end = Math.min(i + chunkSize, list.size());
            chunks.add(list.subList(i, end));
        }
        return chunks;
    }

    private void saveChunks(String inputFilePath, List<String> header, List<List<List<String>>> chunks) {
        String directoryPath = new File(inputFilePath).getParent();
        String fileNameWithoutExtension = new File(inputFilePath).getName().replace(".csv", "");

        for (int i = 0; i < chunks.size(); i++) {
            String fileName = String.format("%s.%d.csv", fileNameWithoutExtension, i + 1);
            String filePath = new File(directoryPath, fileName).getPath();

            try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath))) {
                writeCsvLine(writer, header);
                for (List<String> row : chunks.get(i)) {
                    writeCsvLine(writer, row);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void writeCsvLine(BufferedWriter writer, List<String> values) throws IOException {
        String line = String.join(",", values);
        writer.write(line);
        writer.newLine();
    }
}
