package dto;

/**
 * purpose of creating this class is to Get an instance which includes only email and password
 */
public class LoginCredentials {

    private String email;

    private String password;



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
