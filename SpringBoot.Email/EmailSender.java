import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

@Service
public class EmailSender {

    @Autowired
    private EmailService emailService;
	
	@Autowired
	private Etl etl;

    public void sendSuccessEmail() throws MessagingException, IOException {
        EmailContext emailContext = new EmailContext();
        EmailTemplateType templateType = EmailTemplateType.SUCCESS;

		Constans constans = new EmailConstantsSuccess();
		emailContext.setVariables(constans);
		
		List<SummaryEntity> summaryEntities = etl.findByStatus(0);
 
        emailContext.setVariable(Constants.SUMMARY_ENTITIES, summaryEntities);

        emailService.sendEmail(emailContext, templateType);
    }
}
