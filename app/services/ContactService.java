package services;

import models.Contact;
import models.User;
import play.api.libs.iteratee.Cont;

import java.util.List;

/**
 * Created by lakjeewa on 8/1/18.
 */
public interface ContactService {
    List<Contact> addContact(Contact contactToAdd, User user);
    List<Contact> viewContacts(User user);
}
