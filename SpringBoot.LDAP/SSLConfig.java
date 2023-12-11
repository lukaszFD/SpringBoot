import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.net.ssl.SSLContext;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;

@Configuration
public class SSLConfig {

    @Value("${ldap.certificate.path}")
    private String ldapCertificatePath;

    @Value("${ldap.certificate.password}")
    private String ldapCertificatePassword;

    @Bean
    public SSLContext sslContext() throws Exception {
        KeyStore keyStore = KeyStore.getInstance("PKCS12");

        try (FileInputStream inputStream = new FileInputStream(ldapCertificatePath)) {
            keyStore.load(inputStream, ldapCertificatePassword.toCharArray());
        }

        return SSLContextBuilder
                .create()
                .loadKeyMaterial(keyStore, ldapCertificatePassword.toCharArray())
                .build();
    }
}
