package services;

import dto.LoginCredentials;
import models.User;

/**
 * Created by lakjeewa on 8/1/18.
 */
public interface UserService {
    User addUser(User userToAdd);
    User login(LoginCredentials logincredentials);
}
