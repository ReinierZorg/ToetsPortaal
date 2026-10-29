package resources;

import domain.Question;
import domain.SchoolTest;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.SecurityContext;
import services.TestService;
import services.UserService;

import java.util.ArrayList;
import java.util.List;

@Path("tests")
public class TestResource {

    @GET
    @Path("my")
    @RolesAllowed("teacher")
    @Produces(MediaType.APPLICATION_JSON)
    public List<SchoolTest> getMyTests(@Context SecurityContext securityContext) {

        String email = securityContext.getUserPrincipal().getName();

        UserService service = new UserService();
        return service.getTestsByTeacher(email);
    }
    @POST
    @RolesAllowed("teacher")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createTest(
            SchoolTest schoolTest,
            @Context SecurityContext securityContext
    ) {
        System.out.println("POST /tests aangeroepen");

        System.out.println("SecurityContext = " + securityContext);

        if (securityContext == null) {
            System.out.println("SecurityContext is NULL");
        } else {
            System.out.println("Principal = " + securityContext.getUserPrincipal());
        }

        if (securityContext == null || securityContext.getUserPrincipal() == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        String email = securityContext.getUserPrincipal().getName();

        System.out.println("Email = " + email);

        TestService service = new TestService();

        service.createTest(schoolTest, email );

        return Response.status(Response.Status.CREATED).build();
    }


}
