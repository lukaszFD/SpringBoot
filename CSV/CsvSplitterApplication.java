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
            System.out.println("Podaj plik CSV jako argument.");
            return;
        }

        File inputFile = new File(args[0]);
        if (!inputFile.exists() || !inputFile.isFile()) {
            System.out.println("Podany plik nie istnieje lub nie jest plikiem.");
            return;
        }

        splitCsvFile(inputFile);
    }

    private void splitCsvFile(File inputFile) {
        try (BufferedReader reader = Files.newBufferedReader(inputFile.toPath())) {
            List<String> header = readHeader(reader);
            List<List<String>> rows = readRows(reader);

            int chunkSize = rows.size() > 1000000 ? 8 : 4;
            List<List<List<String>>> chunks = chunkList(rows, chunkSize);

            saveChunks(inputFile, header, chunks);

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

    private void saveChunks(File inputFile, List<String> header, List<List<List<String>>> chunks) {
        String directoryPath = inputFile.getParent();

        for (int i = 0; i < chunks.size(); i++) {
            String fileName = String.format("%s.%d.csv", inputFile.getName(), i + 1);
            Path filePath = Paths.get(directoryPath, fileName);

            try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
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
