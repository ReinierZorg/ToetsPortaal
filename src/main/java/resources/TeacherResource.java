package resources;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("teacher")
public class TeacherResource {

    @GET
    @RolesAllowed("teacher")
    public String teacher(){
        return "Welkom docent!";
    }

}