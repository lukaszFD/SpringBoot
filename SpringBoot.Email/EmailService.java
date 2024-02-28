import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
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

    public void sendEmail(EmailTemplateType templateType) throws MessagingException, IOException {
        Constants constants = getConstantsForTemplateType(templateType);
        EmailContext emailContext = new EmailContext();
        emailContext.setVariables(constants);

        String processedHtmlBody = templateEngine.process(templateType.getTemplateName(), emailContext.getContext());
        javaMailSender.send(createMimeMessage(processedHtmlBody, emailContext));
    }

    private MimeMessage createMimeMessage(String htmlBody, EmailContext emailContext) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        helper.setTo(emailContext.getContext().getVariable(Constants.RECIPIENT).toString());
        helper.setSubject(emailContext.getContext().getVariable(Constants.SUBJECT).toString());
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