package resources;

import jakarta.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("api")
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() throws ClassNotFoundException {
        // Logstatement, op auzre/ubuntu by default te vinden onder '/var/log/tomcat10/catalina.out'
        System.out.println("Hello API");
        packages("resources", "security");
        // Hack om geforceerd postgresql in te laden, anders filtert Tomcat deze er uit
        Class.forName("org.postgresql.Driver");
    }
}