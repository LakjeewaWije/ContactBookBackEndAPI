package dao;

import models.Contact;
import models.User;

import java.util.List;

/**
 * Created by lakjeewa on 8/1/18.
 */
public interface ContactDao {
    Contact addContact(Contact contactToAdd);
    User findUserByToken(String email);
    User updateUser(User toUpdateUser);
    List<Contact> findContactDetailById(long id);
}
