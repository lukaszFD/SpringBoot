import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

@Service
public class EmailSender {

    @Autowired
    private EmailService emailService;

    public void sendSuccessEmail(List<SummaryEntity> summaryEntities) throws MessagingException, IOException {
        EmailContext emailContext = new EmailContext();
        EmailTemplateType templateType = EmailTemplateType.SUCCESS;

        emailContext.setVariables(templateType);

        emailContext.setVariable(Constants.NAME, "Jan Kowalski");
        emailContext.setVariable(Constants.CONTENT_STYLE, "red"); 
        emailContext.setVariable(Constants.SUMMARY_ENTITIES, summaryEntities);

        emailService.sendEmail(emailContext, templateType);
    }
}
