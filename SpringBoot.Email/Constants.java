import java.util.Map;

public interface Constants {
    String RECIPIENT = "recipient";
    String SUBJECT = "subject";
    String NAME = "name";
    String TITLE = "title";
    String CONTENT = "content";
    String CONTENT_STYLE = "contentStyle";
    String STYLE = "style";
    String SUMMARY_ENTITIES = "summaryEntities";

    Map<String, Object> getVariables();
}
