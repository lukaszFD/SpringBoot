import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LiquibaseTableFinder {

    public static void main(String[] args) {
        List<String> tableNames = findLiquibaseTableNames("ścieżka/do/folderu/migracji");
        for (String tableName : tableNames) {
            System.out.println("Znaleziona tabela w skryptach Liquibase: " + tableName);
        }
    }

    public static List<String> findLiquibaseTableNames(String migrationsFolderPath) {
        List<String> tableNames = new ArrayList<>();

        File migrationsFolder = new File(migrationsFolderPath);
        if (migrationsFolder.isDirectory()) {
            File[] files = migrationsFolder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().endsWith(".xml")) {
                        try {
                            BufferedReader reader = new BufferedReader(new FileReader(file));
                            String line;
                            while ((line = reader.readLine()) != null) {
                                String tableName = extractTableNameFromLine(line);
                                if (tableName != null) {
                                    tableNames.add(tableName);
                                }
                            }
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        return tableNames;
    }

    private static String extractTableNameFromLine(String line) {
        // Przyjmujemy, że nazwa tabeli występuje po tagu "<createTable tableName=" lub "<alterTable tableName="
        Pattern pattern = Pattern.compile("<(createTable|alterTable)\\s+tableName=\"(\\w+)\"");
        Matcher matcher = pattern.matcher(line);

        if (matcher.find()) {
            return matcher.group(2);
        }

        return null;
    }
}
