import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import javax.mail.MessagingException;
import java.io.IOException;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    @Autowired
    public EmailService(JavaMailSender javaMailSender, TemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    public void sendEmail(String to, String subject, EmailContext emailContext, EmailTemplateType templateType) throws MessagingException, IOException {
        Constants constants = getConstantsForTemplateType(templateType);
        emailContext.setVariables(constants);

        String processedHtmlBody = templateEngine.process(templateType.getTemplateName(), emailContext.getContext());
        javaMailSender.send(createMimeMessage(to, subject, processedHtmlBody));
    }

    private MimeMessage createMimeMessage(String to, String subject, String htmlBody) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlBody, true);

        return mimeMessage;
    }

    private Constants getConstantsForTemplateType(EmailTemplateType templateType) {
        switch (templateType) {
            case SUCCESS:
                return new EmailConstantsSuccess();
            default:
                throw new IllegalArgumentException("Nieobsługiwany typ szablonu e-maila: " + templateType);
        }
    }
}
