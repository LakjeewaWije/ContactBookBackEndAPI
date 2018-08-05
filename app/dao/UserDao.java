package dao;

import models.Contact;
import models.User;

import java.util.List;

/**
 * Created by lakjeewa on 8/1/18.
 */
public interface UserDao {
    User addUser(User toAddUser);

    User logoutUser(User toLogoutUser);

    User findUserByEmail(String email);

    User updateUser(User toUpdateUser);

    User findUserByToken(String token);

    User deleteUser(User user);

}
