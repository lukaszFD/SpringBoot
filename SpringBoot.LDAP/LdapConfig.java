import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Configuration
public class LdapConfig {

    @Value("${ldap.url}")
    private String ldapUrl;

    @Value("${ldap.base}")
    private String ldapBase;

    @Value("${ldap.userDn}")
    private String ldapUserDn;

    @Value("${ldap.password}")
    private String ldapPassword;

    @Value("${ldap.certificate.path}")
    private String ldapCertificatePath;

    @Bean
    public LdapContextSource contextSource() {
        LdapContextSource contextSource = new LdapContextSource();
        contextSource.setUrl(ldapUrl);
        contextSource.setBase(ldapBase);
        contextSource.setUserDn(ldapUserDn);
        contextSource.setPassword(ldapPassword);

        // Ustawienia dla uwierzytelniania przy użyciu certyfikatu .cer
        contextSource.setPooled(true);
        contextSource.setBaseEnvironmentProperties(getBaseEnvironmentProperties());

        contextSource.afterPropertiesSet();
        return contextSource;
    }

    @Bean
    public LdapTemplate ldapTemplate(ContextSource contextSource) {
        return new LdapTemplate(contextSource);
    }

    private Properties getBaseEnvironmentProperties() {
        Properties environmentProperties = new Properties();
        environmentProperties.setProperty("java.naming.ldap.factory.socket", CustomSocketFactory.class.getName());
        environmentProperties.setProperty("java.naming.security.protocol", "ssl");
        environmentProperties.setProperty("java.naming.security.authentication", "simple");

        try {
            // Wczytaj zawartość certyfikatu .cer
            ClassPathResource resource = new ClassPathResource(ldapCertificatePath);
            byte[] certBytes = FileCopyUtils.copyToByteArray(resource.getInputStream());
            String certString = new String(certBytes, StandardCharsets.UTF_8);
            
            // Dodaj certyfikat do właściwości
            environmentProperties.setProperty("java.naming.ldap.factory.socket", CustomSocketFactory.class.getName());
            environmentProperties.setProperty("java.naming.ldap.factory.socket", CustomSocketFactory.class.getName());
            environmentProperties.setProperty("java.naming.ldap.factory.socket", CustomSocketFactory.class.getName());
            environmentProperties.setProperty("java.naming.ldap.factory.socket", CustomSocketFactory.class.getName());

        } catch (IOException e) {
            e.printStackTrace(); // Tutaj obsłuż błąd wczytywania certyfikatu
        }

        return environmentProperties;
    }
}
