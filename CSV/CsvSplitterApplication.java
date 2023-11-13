import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
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
        if (args.length != 1) {
            System.err.println("Usage: java -jar CsvSplitterApplication.jar <input_csv_file>");
            System.exit(1);
        }

        String inputFilePath = args[0];
        File inputFile = new File(inputFilePath);

        if (!inputFile.exists()) {
            System.err.println("Input file does not exist.");
            System.exit(1);
        }

        splitCsvFile(inputFile);
    }

    private void splitCsvFile(File inputFile) throws IOException {
        try (Reader reader = new FileReader(inputFile);
             CSVPrinter printer = createCsvPrinter(inputFile)) {

            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withHeader().parse(reader);

            List<CSVRecord> recordList = new ArrayList<>();
            int batchSize = calculateBatchSize(records);

            int fileCounter = 1;
            int totalRowCount = 0;

            for (CSVRecord record : records) {
                recordList.add(record);
                totalRowCount++;

                if (recordList.size() >= batchSize) {
                    writeBatchToFile(recordList, printer, inputFile.getName(), fileCounter++);
                    recordList.clear();
                }
            }

            if (!recordList.isEmpty()) {
                writeBatchToFile(recordList, printer, inputFile.getName(), fileCounter);
            }

            System.out.println("File split completed. Total rows: " + totalRowCount);
        }
    }

    private int calculateBatchSize(Iterable<CSVRecord> records) {
        int totalRows = 0;
        for (CSVRecord record : records) {
            totalRows++;
        }

        return totalRows < 1000000 ? 4 : 8;
    }

    private CSVPrinter createCsvPrinter(File inputFile) throws IOException {
        Path outputPath = Paths.get(inputFile.getParent());
        String outputFileName = inputFile.getName().replace(".csv", ".%d.csv");
        File outputFile = new File(outputPath.toFile(), String.format(outputFileName, 1));

        return new CSVPrinter(new FileWriter(outputFile), CSVFormat.DEFAULT.withHeader());
    }

    private void writeBatchToFile(List<CSVRecord> records, CSVPrinter printer, String baseFileName, int fileCounter) throws IOException {
        String fileName = baseFileName.replace(".csv", "." + fileCounter + ".csv");

        for (CSVRecord record : records) {
            printer.printRecord(record.toMap());
        }

        System.out.println("File created: " + fileName);
    }
}
