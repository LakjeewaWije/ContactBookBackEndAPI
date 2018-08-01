package dao.Impl;

import com.avaje.ebean.Model;
import dao.UserDao;
import models.Contact;
import models.User;

/**
 * Created by lakjeewa on 8/1/18.
 */
public class UserDoaImpl implements UserDao{
    public static final Model.Finder<Long, User> find = new Model.Finder<>(User.class);
    @Override
    public User addUser(User toAddUser) {
        toAddUser.save();
        return toAddUser;
    }

    @Override
    public User findUserByEmail(String email) {
        User user = find.where().eq("email",email).findUnique();
        return user;
    }

    @Override
    public User updateUser(User toUpdateUser) {
        toUpdateUser.update();
        return toUpdateUser;
    }

    @Override
    public User findUserByToken(String token) {
        return find.where().eq("authToken",token).findUnique();
    }


}
