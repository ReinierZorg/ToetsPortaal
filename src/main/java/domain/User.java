package domain;

import java.security.Principal;

public class User implements Principal {
    private int user_id;
    private String first_name;
    private String last_name;
    private String email;
    private String password;
    private String role;

    public User(int user_id, String first_name, String last_name, String email, String password, String role) {
        this.user_id = user_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getEmail() {return email;}

    public String getRole() {return role;}

    @Override
    public String getName() {
        return email;
    }
}
