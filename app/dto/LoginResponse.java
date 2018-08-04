package dto;

import models.User;

/**
 *purpose of this class is to create an object which includes user,auth token to wrap it as a json
 */
public class LoginResponse {

    private User user;



    public LoginResponse(User user, String auth) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
