package controllers;

import authentication.UserAuthentication;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.Contact;
import models.User;
import play.Logger;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;
import services.ContactService;
import services.UserService;
import utils.JsonServiceUtil;
import utils.ResponseWrapper;

import javax.inject.Inject;

import java.util.List;

import static play.mvc.Http.Context.Implicit.ctx;
import static play.mvc.Results.ok;

/**
 * Created by lakjeewa on 8/1/18.
 */
public class ContactController extends Controller{
    @Inject
    private ObjectMapper objectMapper;
    @Inject
    private ContactService contactService;
    /**
     * catching the Json which comes from the front end and assigning a object for the particular Json and passing it to the service
     * @return sending a User object for the UserService
     */
    @With(UserAuthentication.class)
    @BodyParser.Of(BodyParser.Json.class)
    public Result addContact() {

        JsonNode jsonNode = request().body().asJson();

        Contact contactToAdd = null;

        try {
            contactToAdd = objectMapper.treeToValue(jsonNode, Contact.class);
            User loggedInUser = (User) ctx().args.get("user");
            List<Contact> contactList = contactService.addContact(contactToAdd,loggedInUser );
            if (contactList == null){
                return badRequest(JsonServiceUtil.toJsonNode(new ResponseWrapper<>("Not JSon", null)));
            }else {
                return ok(JsonServiceUtil.toJsonNode(new ResponseWrapper<>("Contact added", contactList)));
            }
        } catch (JsonProcessingException e) {
            Logger.error(e.getMessage());
            return badRequest(JsonServiceUtil.toJsonNode(new ResponseWrapper<>("Not JSon", null)));
        }
    }
    @With(UserAuthentication.class)
    public Result getContacts(){
        User loggedInUser = (User) ctx().args.get("user");
        List<Contact> contactList = contactService.viewContacts(loggedInUser);
        return ok(JsonServiceUtil.toJsonNode(new ResponseWrapper<>("All contacts", contactList)));
    }


    @BodyParser.Of(BodyParser.Json.class)
    public Result updateContact(){
        JsonNode jsonNode = request().body().asJson();

        Contact contactToAdd = null;
        try{
            contactToAdd = objectMapper.treeToValue(jsonNode, Contact.class);
            List<Contact> contactList = contactService.updateContact(contactToAdd);
            return ok(JsonServiceUtil.toJsonNode(new ResponseWrapper<>("All contacts", contactList)));
        }catch (Exception e){
            return null;
        }
    }

    public Result deleteContact(Long id){
        try{
            List<Contact> newContactList = contactService.deleteContact(id);
            if (newContactList != null) {
                return ok(JsonServiceUtil.toJsonNode(new ResponseWrapper<>("All contacts", newContactList)));
            }else {
                return null;
            }
        }catch (Exception e){
            return null;
        }
    }
}
