package controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.LoginCredentials;
import dto.LoginResponse;
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


    /**
     * Getting the Json which comes from the front end and assiging it to a LoginCredential object and passing it to the Service
     * @return Doing all the validations and returning a json with the specific status code
     */
    @BodyParser.Of(BodyParser.Json.class)
    public Result login(){
        JsonNode jsonNode = request().body().asJson();

        try {
            LoginCredentials loginCridentials = objectMapper.treeToValue(jsonNode, LoginCredentials.class);

            User loggedInUser = userService.login(loginCridentials);

            LoginResponse logingResponse = new LoginResponse(loggedInUser,loggedInUser.getAuthToken());


            if (loggedInUser == null){
                return badRequest(JsonServiceUtil.toJsonNode(new ResponseWrapper<>("No user for the provided email/password", null)));
            }
            return ok(JsonServiceUtil.toJsonNode(new ResponseWrapper<>("User loggedIn", logingResponse)));
        } catch (JsonProcessingException e) {
            Logger.error(e.getMessage());
            return badRequest(JsonServiceUtil.toJsonNode(new ResponseWrapper<>("Can not get data", null)));
        }
    }
}
