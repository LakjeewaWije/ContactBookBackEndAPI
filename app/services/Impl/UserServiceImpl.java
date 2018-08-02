package services.Impl;

import dao.UserDao;
import dto.LoginCredentials;
import models.User;
import org.apache.commons.codec.binary.Base64;
import services.UserService;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Date;

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

    @Override
    public User login(LoginCredentials logincredentials) {
        User userInDb = userDao.findUserByEmail(logincredentials.getEmail());
        if (userInDb == null) {
            return null;
        }
        if (!userInDb.getPassword().equals(logincredentials.getPassword())) {
            return null;
        }
        userInDb.setAuthToken(String.valueOf(Base64.encodeBase64((new Date().getTime() + userInDb.getEmail() + userInDb.getPassword() + userInDb.getFirstName()).getBytes())));
        return userDao.updateUser(userInDb);
    }

    @Override
    public User logout(User userToLogout) {
        return userDao.logoutUser(userToLogout);
    }

    @Override
    public User findUserByEmail(String email) {
        return userDao.findUserByEmail(email);
    }
}
