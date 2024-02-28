public enum EmailTemplateType {
    SUCCESS("email-success.html"),
    FAILURE("email-failure.html"),
    SUMMARY("email-summary.html");

    private final String templateName;

    EmailTemplateType(String templateName) {
        this.templateName = templateName;
    }

    public String getTemplateName() {
        return templateName;
    }
}
