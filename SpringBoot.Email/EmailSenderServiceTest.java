import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.mail.MessagingException;

import org.junit.Before;
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

    private SummaryEntity summaryEntity;

    @Before
    public void setUp() {
        // Inicjalizacja obiektu SummaryEntity
        summaryEntity = new SummaryEntity(/* uzupełnij dane */);
    }

    @Test
    public void sendSuccessEmail_Success() throws MessagingException, IOException {
        // Arrange
        EmailContext emailContext = new EmailContext();

        // Ustawienie templateType na SUCCESS
        EmailTemplateType templateType = EmailTemplateType.SUCCESS;

        Constants constants = new EmailConstantsSuccess();
        emailContext.setVariables(constants);

        List<SummaryEntity> summaryEntities = Arrays.asList(summaryEntity);

        when(etl.findByStatus(0)).thenReturn(summaryEntities);

        // Act
        emailSender.sendSuccessEmail();

        // Assert
        // Upewnij się, że sendEmail zostało wywołane z odpowiednimi parametrami
        verify(emailService).sendEmail(emailContext, templateType);
    }

    // Dodaj inne testy jednostkowe w razie potrzeby.
}
