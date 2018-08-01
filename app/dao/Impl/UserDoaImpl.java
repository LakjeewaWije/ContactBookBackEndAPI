package dao.Impl;

import dao.UserDao;
import models.User;

/**
 * Created by lakjeewa on 8/1/18.
 */
public class UserDoaImpl implements UserDao{
    @Override
    public User addUser(User toAddUser) {
        toAddUser.save();
        return toAddUser;
    }
}
