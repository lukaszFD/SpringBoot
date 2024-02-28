public class EmailSender {

    @Autowired
    private EmailService emailService;

    public void sendSuccessEmail(String recipient, String subject, List<SummaryEntity> summaryEntities) throws MessagingException, IOException {
        EmailContext emailContext = new EmailContext();
        EmailTemplateType templateType = EmailTemplateType.SUCCESS;

        emailContext.setVariable(Constants.NAME, "Jan Kowalski");
        emailContext.setVariable(Constants.CONTENT_STYLE, "red"); // Przykładowa zmiana stylu treści
        emailContext.setVariable(Constants.SUMMARY_ENTITIES, summaryEntities);
		
        EmailTemplate emailTemplate = new EmailTemplate(templateType.getTemplateName());

        emailService.sendEmail(recipient, subject, emailContext, emailTemplate);
    }
}
