package dao;

import models.Contact;
import models.User;
import play.api.libs.iteratee.Cont;

import java.util.List;

/**
 * Created by lakjeewa on 8/1/18.
 */
public interface ContactDao {
    Contact addContact(Contact contactToAdd);
    User findUserByToken(String email);
    Contact findContactById(Long id);
    List<Contact> updateContact(Contact toUpdateUser);
    List<Contact> deleteContact(Contact toDeleteContact);
    List<Contact> findContactDetailById(long id);
}
