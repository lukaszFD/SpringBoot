import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/send")
    public String sendEmail() {
        // Wywołaj metodę sendEmail z odpowiednimi danymi
        emailService.sendEmail("recipient@example.com", "Temat e-maila", "Treść e-maila");
        return "E-mail wysłany pomyślnie!";
    }
}
