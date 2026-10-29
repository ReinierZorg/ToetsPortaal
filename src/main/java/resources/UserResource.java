package resources;

import domain.User;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import services.UserService;

import java.util.ArrayList;

@Path("users")
public class UserResource {
    private UserService service = new UserService();

    @GET
    public ArrayList<User> getUsers() {
        return service.getUsers();
    }
}