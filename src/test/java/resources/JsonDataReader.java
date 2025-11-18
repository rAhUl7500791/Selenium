package resources;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonDataReader {

    /**
     * Reads the JSON data file and converts it to a List of HashMaps.
     */
    public static List<HashMap<String, String>> getJsonData(String filePath) throws IOException {
        // Read JSON file content to String
        String jsonContent = FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);

        // Convert String to List<HashMap<String, String>> using Jackson Databind
        ObjectMapper mapper = new ObjectMapper();
        List<HashMap<String, String>> data = mapper.readValue(jsonContent, 
                new TypeReference<List<HashMap<String, String>>>() {});
        
        return data;
    }
}