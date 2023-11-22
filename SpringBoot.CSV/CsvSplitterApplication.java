import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class CsvSplitterApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(CsvSplitterApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        if (args.length != 1) {
            System.err.println("Usage: java -jar CsvSplitterApplication.jar <input_csv_file>");
            System.exit(1);
        }

        String inputFilePath = args[0];
        Path inputPath = Paths.get(inputFilePath);

        if (!Files.exists(inputPath)) {
            System.err.println("Input file does not exist.");
            System.exit(1);
        }

        splitCsvFile(inputPath);
    }

    private void splitCsvFile(Path inputPath) throws Exception {
        List<String> lines = Files.readAllLines(inputPath);
        String header = lines.get(0);

        List<String> records = lines.stream().skip(1).collect(Collectors.toList());

        int batchSize = calculateBatchSize(records.size());

        List<List<String>> batches = partition(records, batchSize);

        int fileCounter = 1;
        int totalRowCount = 0;

        for (List<String> batch : batches) {
            writeBatchToFile(batch, header, inputPath, fileCounter++);
            totalRowCount += batch.size();
        }

        System.out.println("File split completed. Total rows: " + totalRowCount);
    }

    private int calculateBatchSize(int totalRows) {
        return totalRows < 1000000 ? 4 : 8;
    }

    private List<List<String>> partition(List<String> records, int batchSize) {
        List<List<String>> batches = new ArrayList<>();
        for (int i = 0; i < records.size(); i += batchSize) {
            int end = Math.min(i + batchSize, records.size());
            batches.add(records.subList(i, end));
        }
        return batches;
    }

    private void writeBatchToFile(List<String> records, String header, Path inputPath, int fileCounter) throws Exception {
        String fileName = inputPath.getFileName().toString().replace(".csv", "." + fileCounter + ".csv");
        Path outputPath = inputPath.getParent().resolve(fileName);

        List<String> outputLines = new ArrayList<>();
        outputLines.add(header);
        outputLines.addAll(records);

        Files.write(outputPath, outputLines, StandardOpenOption.CREATE, StandardOpenOption.WRITE);

        System.out.println("File created: " + fileName);
    }
}
