import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

public class JsonToCsvConverter {

    public static void convertJsonToCsv(String jsonFilePath) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(new File(jsonFilePath));

            // Extract CSV file name from JSON file
            String csvFileName = getFileNameWithoutExtension(jsonFilePath);

            // OpenCSV writer for CSV output
            try (CSVWriter writer = new CSVWriter(new FileWriter(csvFileName + ".csv"))) {
                // Write header
                writeHeaders(jsonNode, writer);

                // Write data
                writeData(jsonNode, writer);

                System.out.println("Conversion successful. Check '" + csvFileName + ".csv'.");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Conversion failed.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Invalid JSON file.");
        }
    }

    private static void writeHeaders(JsonNode jsonNode, CSVWriter writer) {
        Iterator<String> fieldNames = jsonNode.fieldNames();
        String[] headers = new String[jsonNode.size()];
        int index = 0;

        while (fieldNames.hasNext()) {
            headers[index++] = fieldNames.next();
        }

        writer.writeNext(headers);
    }

    private static void writeData(JsonNode jsonNode, CSVWriter writer) {
        for (JsonNode record : jsonNode) {
            Iterator<JsonNode> elements = record.elements();
            String[] rowData = new String[record.size()];
            int index = 0;

            while (elements.hasNext()) {
                rowData[index++] = elements.next().asText();
            }

            writer.writeNext(rowData);
        }
    }

    private static String getFileNameWithoutExtension(String filePath) {
        File file = new File(filePath);
        String fileName = file.getName();
        int lastDotIndex = fileName.lastIndexOf(".");
        return lastDotIndex != -1 ? fileName.substring(0, lastDotIndex) : fileName;
    }

    public static void main(String[] args) {
        // Replace with your JSON file path
        String jsonFilePath = "path/to/your/file.json";
        convertJsonToCsv(jsonFilePath);
    }
}
