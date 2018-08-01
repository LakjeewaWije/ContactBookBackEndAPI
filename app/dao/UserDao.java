package dao;

import models.User;

/**
 * Created by lakjeewa on 8/1/18.
 */
public interface UserDao {
    User addUser(User toAddUser);

    User findUserByEmail(String email);

    User updateUser(User toUpdateUser);
}
