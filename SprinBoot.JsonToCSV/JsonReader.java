import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JsonReader {

    public static List<YourModelClass> readJson(String jsonFilePath) {
        List<YourModelClass> modelList = new ArrayList<>();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(new File(jsonFilePath));

            Iterator<JsonNode> elements = jsonNode.elements();
            while (elements.hasNext()) {
                JsonNode element = elements.next();

                // Map JSON attributes to YourModelClass fields
                YourModelClass modelObject = mapJsonToModel(element);

                // Add the model object to the list
                modelList.add(modelObject);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return modelList;
    }

    private static YourModelClass mapJsonToModel(JsonNode jsonNode) {
        // Replace with your own logic to map JSON attributes to YourModelClass fields
        YourModelClass modelObject = new YourModelClass();
        modelObject.setField1(jsonNode.get("field1").asText());
        modelObject.setField2(jsonNode.get("field2").asInt());
        // Add mappings for other fields as needed

        return modelObject;
    }

    public static void main(String[] args) {
        // Replace with your JSON file path
        String jsonFilePath = "path/to/your/file.json";
        
        // Read JSON and get a list of YourModelClass objects
        List<YourModelClass> modelList = readJson(jsonFilePath);

        // Print or use the list as needed
        for (YourModelClass modelObject : modelList) {
            System.out.println(modelObject);
        }
    }
}
