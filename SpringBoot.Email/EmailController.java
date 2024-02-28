@RestController
@RequestMapping("/email")
public class EmailController {

    private final EmailService emailService;
    private final EmailSender emailSender;

    @Autowired
    public EmailController(EmailService emailService, EmailSender emailSender) {
        this.emailService = emailService;
        this.emailSender = emailSender;
    }

    @PostMapping("/send-summary-entities")
    public String sendSummaryEntitiesEmail(@RequestParam String recipient, @RequestParam String subject, @RequestParam String name) {
        try {
            
            List<SummaryEntity> summaryEntities = // Pobierz dane z innego API;

          
            emailSender.sendSuccessEmail(recipient, subject, summaryEntities);
            return "E-mail wysłany pomyślnie!";
        } catch (Exception e) {
            return "Błąd podczas wysyłania e-maila: " + e.getMessage();
        }
    }
}
