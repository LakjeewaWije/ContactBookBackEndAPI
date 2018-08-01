package controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.User;
import play.Logger;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import services.UserService;
import utils.JsonServiceUtil;
import utils.ResponseWrapper;

import javax.inject.Inject;

/**
 * Created by lakjeewa on 8/1/18.
 */
public class UserController extends Controller{
    @Inject
    private ObjectMapper objectMapper;


    @Inject
    private UserService userService;


    /**
     * catching the Json which comes from the front end and assigning a object for the particular Json and passing it to the service
     * @return sending a User object for the UserService
     */
    @BodyParser.Of(BodyParser.Json.class)
    public Result addUser() {

        JsonNode jsonNode = request().body().asJson();

        User userToAdd = null;

        try {
            userToAdd = objectMapper.treeToValue(jsonNode, User.class);

            User addedUser = userService.addUser(userToAdd);

            return ok(JsonServiceUtil.toJsonNode(new ResponseWrapper<>("User added", addedUser)));

        } catch (JsonProcessingException e) {
            Logger.error(e.getMessage());
            return badRequest(JsonServiceUtil.toJsonNode(new ResponseWrapper<>("Not JSon", null)));
        }
    }
}
