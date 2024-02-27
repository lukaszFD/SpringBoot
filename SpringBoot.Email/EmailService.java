import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    @Autowired
    public EmailService(JavaMailSender javaMailSender, TemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    public void sendStyledHtmlEmail(String to, String subject, String name, String content, String contentStyle) throws MessagingException, IOException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        helper.setTo(to);
        helper.setSubject(subject);

        // Odczytaj zawartość pliku HTML
        String htmlBody = readHtmlFile("email-template.html");

        // Ustaw dynamiczne dane w treści HTML
        Context context = new Context();
        context.setVariable("name", name);
        context.setVariable("title", subject);
        context.setVariable("content", content);
        context.setVariable("contentStyle", contentStyle);
        context.setVariable("style", "color: " + (contentStyle.equals("red") ? "red" : "green") + ";"); // Styl dla nagłówka h1
        String processedHtmlBody = templateEngine.process(htmlBody, context);

        // Ustaw treść HTML wiadomości
        helper.setText(processedHtmlBody, true);

        javaMailSender.send(mimeMessage);
    }

    private String readHtmlFile(String filename) throws IOException {
        ClassPathResource resource = new ClassPathResource(filename);
        InputStreamSource source = new InputStreamSource() {
            @Override
            public InputStream getInputStream() throws IOException {
                return resource.getInputStream();
            }
        };
        try (InputStream inputStream = source.getInputStream()) {
            Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8.name()).useDelimiter("\\A");
            return scanner.hasNext() ? scanner.next() : "";
        }
    }
}
