package security;


import jakarta.ws.rs.core.SecurityContext;
import domain.User;
import java.security.Principal;

public class MySecurityContext implements SecurityContext {
    private User user;
    private String scheme;

    /**
     * Constructor, initializes the context
     *
     * @param user The user
     * @param scheme The scheme
     */
    public MySecurityContext(User user, String scheme) {
        this.user = user;
        this.scheme = scheme;
    }

    /**
     * Returns the Principal user
     *
     * @return The Principal User
     */
    @Override
    public Principal getUserPrincipal() {
        return this.user;
    }

    /**
     * Returns true if the User has the role role.
     *
     * @param role The role to check
     * @return true if user has the role
     */
    @Override
    public boolean isUserInRole(String role) {
        if (user.getRole() != null) {
            System.out.printf(
                    "%s equals %s%n"
                    ,role
                    ,user.getRole());
            return role.equals(user.getRole());
        }
        return false;
    }

    /**
     * Returns true if the connection is secure
     *
     * @return true if the connection is secure
     */
    @Override
    public boolean isSecure() {
        return "https".equals(this.scheme);
    }

    /**
     * Returns the authentification scheme
     *
     * @return the authentification scheme
     */
    @Override
    public String getAuthenticationScheme() {
        return SecurityContext.BASIC_AUTH;
    }
}