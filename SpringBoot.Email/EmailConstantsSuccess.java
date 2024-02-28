import java.util.HashMap;
import java.util.Map;

public class EmailConstantsSuccess implements Constants {

    @Override
    public Map<String, String> getAll() {
        Map<String, String> constants = new HashMap<>();
        constants.put(TITLE_SUCCESS, "Tytuł sukcesu");
        constants.put(SOME_OTHER_SUCCESS_VARIABLE, "Inna zmienna sukcesu");
        constants.put(NAME, "Imię");
        constants.put(CONTENT, "Dane z listy SummaryEntity:");
        constants.put(CONTENT_STYLE, "blue");
        constants.put(STYLE, "color: green;");
        constants.put(SUMMARY_ENTITIES, "summaryEntities");
        return constants;
    }
}
