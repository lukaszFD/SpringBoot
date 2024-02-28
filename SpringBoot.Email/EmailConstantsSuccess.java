import java.util.HashMap;
import java.util.Map;

public class EmailConstantsSuccess implements Constants {

    @Override
    public Map<String, String> getAll() {
        Map<String, String> constants = new HashMap<>();
        constants.put(RECIPIENT, "recipient@example.com");
        constants.put(SUBJECT, "Temat e-maila");
        constants.put(NAME, "Imię");
        constants.put(TITLE, "Tytuł sukcesu");
        constants.put(CONTENT, "Dane z listy SummaryEntity:");
        constants.put(CONTENT_STYLE, "blue");
        constants.put(STYLE, "color: green;");
        constants.put(SUMMARY_ENTITIES, "summaryEntities");
        return constants;
    }
}
