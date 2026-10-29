package security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.ext.Provider;
import services.UserService;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {
    @Override
    public void filter(ContainerRequestContext requestCtx) {

        String scheme = requestCtx
                .getUriInfo()
                .getRequestUri()
                .getScheme();

        // Users are treated as guests, unless a valid JWT is provided
        MySecurityContext msc = new MySecurityContext(null, scheme);
        String authHeader = requestCtx
                .getHeaderString(HttpHeaders.AUTHORIZATION);

        if (authHeader != null &&
                authHeader.startsWith("Bearer ")) {

            String token = authHeader
                    .substring("Bearer".length())
                    .trim();

            try {
                // Validate the token
                JwtParser parser = Jwts.parser()
                        .verifyWith(AuthenticationResource.key).build();//Use the same key as when creating the JWT
                Claims claims = parser
                        .parseSignedClaims(token).getPayload();

                String user = claims.getSubject();
                msc = new MySecurityContext(new UserService().getUserByEmail(user), scheme);
                System.out.printf("Valid JWT, processing as %s!%n", user);
            } catch (JwtException | IllegalArgumentException e) {
                System.out.println("Invalid JWT, processing as guest!");
            }
        }

        requestCtx.setSecurityContext(msc);
    }
}
