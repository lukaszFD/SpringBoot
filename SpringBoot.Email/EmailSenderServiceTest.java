import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.mail.MessagingException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class EmailSenderTest {

    @Mock
    private EmailService emailService;

    @Mock
    private Etl etl;

    @InjectMocks
    private EmailSender emailSender;

    @Test
    public void sendSuccessEmail_Success() throws MessagingException, IOException {
        // Arrange
        EmailContext emailContext = new EmailContext();
        EmailTemplateType templateType = EmailTemplateType.SUCCESS;

        Constants constants = new EmailConstantsSuccess();
        emailContext.setVariables(constants);

        List<SummaryEntity> summaryEntities = Arrays.asList(new SummaryEntity(/*...*/)); // Uzupełnij danymi

        when(etl.findByStatus(0)).thenReturn(summaryEntities);

        // Act
        emailSender.sendSuccessEmail();

        // Assert
        verify(emailService).sendEmail(any(EmailContext.class), any(EmailTemplateType.class));
    }

    // Dodaj inne testy jednostkowe w razie potrzeby.
}
