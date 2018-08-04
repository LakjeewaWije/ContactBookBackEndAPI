package dao;

import models.User;

/**
 * Created by lakjeewa on 8/1/18.
 */
public interface UserDao {
    User addUser(User toAddUser);

    User logoutUser(User toLogoutUser);

    User findUserByEmail(String email);

    User updateUser(User toUpdateUser);

    User findUserByToken(String token);
}
