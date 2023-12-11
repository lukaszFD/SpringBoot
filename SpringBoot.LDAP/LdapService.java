import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Service;

@Service
public class LdapService {

    private final LdapTemplate ldapTemplate;

    @Autowired
    public LdapService(LdapTemplate ldapTemplate) {
        this.ldapTemplate = ldapTemplate;
        testLdapConnection();
    }

    private void testLdapConnection() {
        try {
            ldapTemplate.lookup("");
            System.out.println("Połączenie z serwerem LDAP udane.");
        } catch (Exception e) {
            System.err.println("Błąd podczas próby połączenia z serwerem LDAP: " + e.getMessage());
        }
    }

    public String getUserFullName(String username) {
        return ldapTemplate.search(
                "ou=people",
                "(uid=" + username + ")",
                (contextMapper) -> contextMapper.getAttribute("cn")
        ).stream().findFirst().orElse(null);
    }
}
