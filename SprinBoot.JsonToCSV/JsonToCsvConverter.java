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

            // OpenCSV writer for CSV output
            try (CSVWriter writer = new CSVWriter(new FileWriter(getCsvFilePath(jsonFilePath)))) {
                // Write header
                writeHeaders(jsonNode, writer);

                // Write data
                writeData(jsonNode, writer);

                System.out.println("Conversion successful. Check '" + getCsvFilePath(jsonFilePath) + "'.");
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

    private static String getCsvFilePath(String jsonFilePath) {
        File jsonFile = new File(jsonFilePath);
        String parentDirectory = jsonFile.getParent();
        String jsonFileName = jsonFile.getName();
        int lastDotIndex = jsonFileName.lastIndexOf(".");
        String baseName = lastDotIndex != -1 ? jsonFileName.substring(0, lastDotIndex) : jsonFileName;
        return parentDirectory + File.separator + baseName + ".csv";
    }
}
