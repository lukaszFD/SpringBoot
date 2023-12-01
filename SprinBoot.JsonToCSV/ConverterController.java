import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVWriter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

@RestController
public class ConverterController {

    @PostMapping("/convert")
    public String convertJsonToCsv(@RequestBody String jsonString) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonString);

            // OpenCSV writer for CSV output
            try (CSVWriter writer = new CSVWriter(new FileWriter("output.csv"))) {
                // Write header
                writeHeaders(jsonNode, writer);

                // Write data
                writeData(jsonNode, writer);

                return "Conversion successful. Check 'output.csv'.";
            } catch (IOException e) {
                e.printStackTrace();
                return "Conversion failed.";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Invalid JSON format.";
        }
    }

    private void writeHeaders(JsonNode jsonNode, CSVWriter writer) {
        Iterator<String> fieldNames = jsonNode.fieldNames();
        String[] headers = new String[jsonNode.size()];
        int index = 0;

        while (fieldNames.hasNext()) {
            headers[index++] = fieldNames.next();
        }

        writer.writeNext(headers);
    }

    private void writeData(JsonNode jsonNode, CSVWriter writer) {
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
}
