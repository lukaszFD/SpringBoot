import java.io.*;
import java.nio.charset.StandardCharsets;

public class CsvPreprocessor {

    public static InputStreamReader preprocessCsv(InputStreamReader input) throws IOException {
        BufferedReader reader = new BufferedReader(input);
        StringWriter stringWriter = new StringWriter();
        BufferedWriter writer = new BufferedWriter(stringWriter);

        String line;
        while ((line = reader.readLine()) != null) {
            writer.write(line.replaceAll("\r(?!\n)", "").replaceAll("(?<!\r)\n", ""));
            writer.newLine();
        }
        
        writer.flush();
        return new InputStreamReader(new ByteArrayInputStream(stringWriter.toString().getBytes(StandardCharsets.UTF_8)));
    }

    public static void main(String[] args) throws IOException {
        String csvData = "Column1\rColumn2\nColumn3\r\nColumn4\rColumn5\nColumn6";
        InputStreamReader inputStreamReader = new InputStreamReader(new ByteArrayInputStream(csvData.getBytes(StandardCharsets.UTF_8)));
        InputStreamReader processedReader = preprocessCsv(inputStreamReader);

        BufferedReader finalReader = new BufferedReader(processedReader);
        String processedLine;
        while ((processedLine = finalReader.readLine()) != null) {
            System.out.println(processedLine);
        }
    }
}