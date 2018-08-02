package services.Impl;

import authentication.UserAuthentication;
import dao.ContactDao;
import models.Contact;
import models.User;
import play.mvc.With;
import services.ContactService;

import javax.inject.Inject;
import javax.inject.Named;

import java.util.ArrayList;
import java.util.List;

import static play.mvc.Http.Context.Implicit.ctx;

/**
 * Created by lakjeewa on 8/1/18.
 */
public class ContactServiceImpl implements ContactService {
    Contact n = new Contact();
    @Inject
    @Named("contactDao")
    private ContactDao contactDao;
    @Override
    public List<Contact> addContact(Contact contactToAdd, User user) {

        Contact contact = contactToAdd;
        contact.setUser(user);
        if (user.getUserId() != null){
            contactDao.addContact(contact);
            List<Contact> contactList = contactDao.findContactDetailById(user.getUserId());
            return contactList;
        }else {
            return null;
        }
    }

    @Override
    public List<Contact> viewContacts(User user) {
        if (user.getUserId() != null){
            List<Contact> contactList = contactDao.findContactDetailById(user.getUserId());
            return contactList;
        }else {
            return null;
        }
    }

    @Override
    public List<Contact> updateContact(Contact contact) {
        List<Contact> contactList = contactDao.updateContact(contact);
        return  contactList;
    }

    @Override
    public List<Contact> deleteContact(Long id) {
        Contact contact = contactDao.findContactById(id);
        List<Contact> contactList = contactDao.deleteContact(contact);
        return  contactList;
    }
}
