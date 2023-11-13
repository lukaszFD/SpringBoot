import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.file.*;
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

        int batchSize = calculateBatchSize(lines);

        try (Stream<String> stream = Files.lines(inputPath)) {
            List<String> records = stream.skip(1).collect(Collectors.toList());

            int fileCounter = 1;
            int totalRowCount = records.size();

            for (int i = 0; i < records.size(); i += batchSize) {
                List<String> batch = records.subList(i, Math.min(i + batchSize, records.size()));
                writeBatchToFile(header, inputPath, fileCounter++, batch);
            }

            System.out.println("File split completed. Total rows: " + totalRowCount);
        }
    }

    private int calculateBatchSize(List<String> lines) {
        return lines.size() < 1000000 ? 4 : 8;
    }

    private void writeBatchToFile(String header, Path inputPath, int fileCounter, List<String> records) throws Exception {
        String fileName = inputPath.getFileName().toString().replace(".csv", "." + fileCounter + ".csv");
        Path outputPath = inputPath.resolveSibling(fileName);

        try (BufferedWriter writer = Files.newBufferedWriter(outputPath)) {
            writer.write(header + "\n");
            records.forEach(record -> {
                try {
                    writer.write(record + "\n");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            System.out.println("File created: " + outputPath.toString());
        }
    }
}
