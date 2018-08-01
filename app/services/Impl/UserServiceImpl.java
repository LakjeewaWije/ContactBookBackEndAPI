package services.Impl;

import dao.UserDao;
import models.User;
import services.UserService;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by lakjeewa on 8/1/18.
 */
public class UserServiceImpl implements UserService {

    @Inject
    @Named("userDao")
    private UserDao userDao;

    @Override
    public User addUser(User userToAdd) {
        return userDao.addUser(userToAdd);
    }
}
