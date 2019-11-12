package sa.student.service;

import com.novell.ldap.*;
import java.io.UnsupportedEncodingException;
import javax.faces.context.FacesContext;

public class LdapService {

    private LDAPConnection lc = new LDAPConnection();


    public Boolean login(String user, String password){
        if (connect()) {
            if (validateUser(user, password)) {
                return true;
            } else {
		System.out.println("usuario no valido");                
		return false;
            }
        } else {
	    System.out.println("no conecto con el ldap");
            return false;
        }
    }

    public Boolean connect() {

        String ldapHost = "34.69.44.104";
        String dn = "cn=admin,dc=arqsoft,dc=unal,dc=edu,dc=co";
        String password = "admin";

        int ldapPort =  LDAPConnection.DEFAULT_PORT;
        int ldapVersion = LDAPConnection.LDAP_V3;

        try {
            lc.connect(ldapHost, ldapPort);
            System.out.println("Connecting to LDAP Server...");
            lc.bind(ldapVersion, dn, password.getBytes("UTF8"));
            System.out.println("Authenticated in LDAP Server...");
            return true;
        } catch (LDAPException | UnsupportedEncodingException ex) {
            System.out.println("ERROR when connecting to LDAP Server...");
            return false;
        }
    }

    public Boolean validateUser(String username, String password){
	System.out.println(username);
        String dn = "cn=" + username + ",ou=academy,dc=arqsoft,dc=unal,dc=edu,dc=co";
        //String dn = "cn=gebejaranod@unal.edu.co,ou=academy,dc=arqsoft,dc=unal,dc=edu,dc=co";
	try {
            lc.bind(dn, password);
	    System.out.println("valido usuario");
            return true;
        } catch (LDAPException ex) {
            System.out.println("no valido usuario");
	    return false;
        }
    }
    
    public String getData(String username){
	System.out.println(username);
        //String dn = "cn=gebejaranod@unal.edu.co,ou=academy,dc=arqsoft,dc=unal,dc=edu,dc=co";
        String dn = "cn=" + username + ",ou=academy,dc=arqsoft,dc=unal,dc=edu,dc=co";
        
	LDAPEntry foundEntry = null;
        LDAPAttribute uid = null;
        String getAttrs[] = { "uid"};
        String values[] = {};
        String a  = "";
        try{
            foundEntry = lc.read(dn, getAttrs);
            uid = foundEntry.getAttribute("uid");
            values = uid.getStringValueArray();
            a = values[0];
            return a;
        } catch (LDAPException ex){
            return "LDAPException found";
        }
    }
}
