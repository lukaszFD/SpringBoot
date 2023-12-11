import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.pool.factory.PoolingContextSource;
import org.springframework.ldap.support.LdapUtils;
import org.springframework.ldap.transaction.compensating.manager.ContextSourceTransactionManager;
import org.springframework.security.ldap.DefaultTlsDirContextAuthenticationStrategy;

import javax.net.SocketFactory;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Collections;

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

    @Value("${ldap.keystore.path}")
    private String keystorePath;

    @Value("${ldap.keystore.password}")
    private String keystorePassword;

    @Value("${ldap.keystore.type}")
    private String keystoreType;

    @Bean
    public LdapTemplate ldapTemplate() throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException, KeyManagementException {
        return new LdapTemplate(contextSource());
    }

    @Bean
    public LdapContextSource contextSource() throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException, KeyManagementException {
        PoolingContextSource poolingContextSource = new PoolingContextSource();
        poolingContextSource.setDirContextValidator(ctx -> {});
        poolingContextSource.setBase(ldapBase);
        poolingContextSource.setUrl(ldapUrl);
        poolingContextSource.setUserDn(ldapUserDn);
        poolingContextSource.setPassword(ldapPassword);
        poolingContextSource.setAuthenticationStrategy(new DefaultTlsDirContextAuthenticationStrategy());

        // Load keystore
        KeyStore keyStore = KeyStore.getInstance(keystoreType);
        try (InputStream keystoreStream = getClass().getClassLoader().getResourceAsStream(keystorePath)) {
            keyStore.load(keystoreStream, keystorePassword.toCharArray());
        }

        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore, keystorePassword.toCharArray());

        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), null);

        poolingContextSource.setSocketFactory(socketFactory(sslContext));

        return poolingContextSource;
    }

    private SocketFactory socketFactory(SSLContext sslContext) {
        return sslContext.getSocketFactory();
    }
}
