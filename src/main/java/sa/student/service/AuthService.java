package sa.student.service;

import sa.student.model.Student;
import sa.student.service.LdapService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class AuthService {

    @PersistenceContext
    EntityManager entityManager;

    String response = "";
    LdapService ldapService = new LdapService();

    public String login(Student student) {

        String username = student.getUsername();
        String password = student.getPassword();

        if (ldapService.connect()) {
            if (ldapService.validateUser(username, password)) {
                response = ldapService.getData(username);
            } else {
                response = "false";
            }
        } else {
            response = "false";
        }
        return response;
    }
}
