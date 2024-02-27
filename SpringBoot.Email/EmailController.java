@RestController
@RequestMapping("/email")
public class EmailController {

    private final EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send-summary-entities")
    public String sendSummaryEntitiesEmail(@RequestParam String recipient, @RequestParam String subject, @RequestParam String name) {
        try {
            // Pobierz dane z innego API (przykładowa lista SummaryEntity)
            List<SummaryEntity> summaryEntities = // Pobierz dane z innego API;

            // Wyślij e-mail z danymi z listy SummaryEntity
            emailService.sendSummaryEntitiesEmail(recipient, subject, name, summaryEntities);
            return "E-mail wysłany pomyślnie!";
        } catch (Exception e) {
            return "Błąd podczas wysyłania e-maila: " + e.getMessage();
        }
    }
}
