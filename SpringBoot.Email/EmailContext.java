import org.thymeleaf.context.Context;

public class EmailContext {

    private Context context;

    public EmailContext() {
        this.context = new Context();
    }

    public Context getContext() {
        return context;
    }

    public void setVariable(String name, Object value) {
        context.setVariable(name, value);
    }

    public void setVariables(Constants constants) {
        context.setVariables(constants.getAll());
    }

    public void setVariables(EmailTemplateType templateType) {
        switch (templateType) {
            case SUCCESS:
                setVariables(new EmailConstantsSuccess());
                break;
            default:
                throw new IllegalArgumentException("Nieobsługiwany typ szablonu e-maila: " + templateType);
        }
    }
}
