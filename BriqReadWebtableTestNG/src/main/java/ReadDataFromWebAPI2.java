import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

public class ReadDataFromWebAPI2 {

    public static void main(String[] args) {
        // Define the URL from which you want to fetch data
        String apiUrl = "https://data.sfgov.org/resource/p4e4-a5a7.json";

        try {
            // Create an HTTP connection to the API URL
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Check if the response is successful (HTTP status code 200)
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                // Read the API response
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // Parse the JSON data using Jackson
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(response.toString());

                // Define the CSV file name based on the current timestamp
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yy-HH-mm-ss");
                String timestamp = dateFormat.format(new Date());
                String csvFileName = "data_" + timestamp + ".csv";

                // Create a CSV file and write the JSON data to it
                CSVWriter csvWriter = new CSVWriter(new FileWriter(csvFileName));

                // Write CSV header (based on JSON keys)
                List<String> headers = new ArrayList<>();
                JsonNode firstObject = jsonNode.elements().next();
                Iterator<Entry<String, JsonNode>> fields = firstObject.fields();
                while (fields.hasNext()) {
                    Entry<String, JsonNode> entry = fields.next();
                    headers.add(entry.getKey());
                }
                csvWriter.writeNext(headers.toArray(new String[0]));

                // Write JSON data to CSV rows
                for (JsonNode element : jsonNode) {
                    List<String> row = new ArrayList<>();
                    fields = element.fields();
                    while (fields.hasNext()) {
                        Entry<String, JsonNode> entry = fields.next();
                        row.add(entry.getValue().asText());
                    }
                    csvWriter.writeNext(row.toArray(new String[0]));
                }

                csvWriter.close();
                System.out.println("Data exported to CSV file: " + csvFileName);
            } else {
                System.err.println("HTTP request failed with status code: " + responseCode);
            }

            // Close the HTTP connection
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
