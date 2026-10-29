package security;

import domain.User;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.security.RolesAllowed;
import jakarta.json.Json;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import services.UserService;

import javax.crypto.SecretKey;
import java.util.Calendar;
import java.util.Collections;

@Path("/auth")
public class AuthenticationResource {
    public static final SecretKey key = Jwts.SIG.HS256.key().build();

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response authenticateUser(LogonRequest logonRequest) {
        try {
            // Try to find the user and get its role
            String role = new UserService().validateLogin(logonRequest.email, logonRequest.password);

            // Throw exception if role is null
            if (role == null) {
                throw new IllegalArgumentException("Validation failed");
            }

            // User is found, create the JWT
            String token = createToken(logonRequest.email, role);

            // Return the token
            return Response.ok()
                    .entity(Collections.singletonMap("JWT", token))
                    .build();

        } catch (JwtException | IllegalArgumentException e) {
            // Something went wrong...
            return Response.status(Response.Status.UNAUTHORIZED)
                    .build();
        }
    }

    private String createToken(String email, String role) {
        Calendar expiration = Calendar.getInstance();
        expiration.add(Calendar.MINUTE, 30);

        return Jwts.builder()
                .subject(email)
                .expiration(expiration.getTime())
                .claim("role", role)
                .signWith(key)
                .compact();
    }
    @GET
    @Path("/hello-protected-world")
    @RolesAllowed("student")
    @Produces("application/json")
    public String helloProtected() {
        return Json.createObjectBuilder().add("message", "Hello verified user, this is world!").build().toString();
    }

    @GET
    @Path("/hello-profile")
    @RolesAllowed("student")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProfile(@Context SecurityContext securityContext) {
        if (securityContext.getUserPrincipal() instanceof User) {
            User currentUser = (User) securityContext.getUserPrincipal();
            return Response.ok(currentUser).build();
//            return Json.createObjectBuilder()
//                    .add("name", currentUser.getName())
//                    .add("password", "Nope")
//                    .build()
//                    .toString();
        }
        return Response.status(Response.Status.FORBIDDEN).build();
    }

    @GET
    @Path("users")
    @RolesAllowed("teacher")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers() {
        return Response.ok(new UserService().getUsers()).build();
    }

}
