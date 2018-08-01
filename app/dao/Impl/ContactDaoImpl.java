package dao.Impl;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Model;
import dao.ContactDao;
import models.Contact;
import models.User;

import java.util.List;

/**
 * Created by lakjeewa on 8/1/18.
 */
public class ContactDaoImpl implements ContactDao {
    public static final Model.Finder<Long, User> find = new Model.Finder<>(User.class);
    public static final Model.Finder<Long, Contact> findd = new Model.Finder<>(Contact.class);
    @Override
    public Contact addContact(Contact contactToAdd) {

        contactToAdd.save();
        return contactToAdd;
    }

    @Override
    public User findUserByToken(String authToken) {
        return find.where().eq("authToken",authToken).findUnique();
    }

    @Override
    public User updateUser(User toUpdateUser) {
        toUpdateUser.update();
        return toUpdateUser;
    }

    @Override
    public List<Contact> findContactDetailById(long id) {
        List<Contact> listCon = findd.where().eq("user_user_id",id).findList();
        return  listCon;
    }
}
