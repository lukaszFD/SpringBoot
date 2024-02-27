@RestController
@RequestMapping("/email")
public class EmailController {

    private final EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send-styled")
    public String sendStyledHtmlEmail(@RequestParam String recipient, @RequestParam String subject, @RequestParam String name) {
        try {
            // Wersja z czerwonym podkreśleniem
            emailService.sendStyledHtmlEmail(recipient, subject, name, "To jest treść HTML z czerwonym podkreśleniem.", "red");

            // Wersja z zielonym podkreśleniem
            emailService.sendStyledHtmlEmail(recipient, subject, name, "To jest treść HTML z zielonym podkreśleniem.", "green");

            return "E-maile wysłane pomyślnie!";
        } catch (Exception e) {
            return "Błąd podczas wysyłania e-maila: " + e.getMessage();
        }
    }
}
