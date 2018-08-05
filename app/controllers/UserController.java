package controllers;

import authentication.UserAuthentication;
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
import play.mvc.With;
import services.UserService;
import utils.JsonServiceUtil;
import utils.ResponseWrapper;

import javax.inject.Inject;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            String email = userToAdd.getEmail();
            String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(email);
//            if (userToAdd.getFirstName() != null) {
                if (matcher.matches()) {
                    User user = userService.findUserByEmail(email);

                    if (user == null) {
                        User addedUser = userService.addUser(userToAdd);
                        return ok(JsonServiceUtil.toJsonNode(new ResponseWrapper<>("User added", addedUser)));
                    } else {
                        return badRequest(JsonServiceUtil.toJsonNode(new ResponseWrapper<>("Email Exists", null)));
                    }
                } else {
                    return badRequest(JsonServiceUtil.toJsonNode(new ResponseWrapper<>("Invalid Email", null)));
                }
//            }else {
//                return badRequest(JsonServiceUtil.toJsonNode(new ResponseWrapper<>("Fill all the fields", null)));
//            }
        } catch (JsonProcessingException e) {
            Logger.error(e.getMessage());
            return badRequest(JsonServiceUtil.toJsonNode(new ResponseWrapper<>("Not JSon", null)));
        } catch (Exception ex){
            Logger.error(ex.getMessage());
            return badRequest(JsonServiceUtil.toJsonNode(new ResponseWrapper<>(ex.getMessage(), null)));
        }
//        return badRequest(JsonServiceUtil.toJsonNode(new ResponseWrapper<>("Can not get data", null)));
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

            User user = userService.findUserByEmail(loginCridentials.getEmail());
            if (user !=null) {
                if (user.getEmail().equals(loginCridentials.getEmail()) && user.getPassword().equals(user.getPassword())) {

                    User loggedInUser = userService.login(loginCridentials);

                    LoginResponse logingResponse = new LoginResponse(loggedInUser, loggedInUser.getAuthToken());

                    return ok(JsonServiceUtil.toJsonNode(new ResponseWrapper<>("User loggedIn", logingResponse)));
                } else {
                    return badRequest(JsonServiceUtil.toJsonNode(new ResponseWrapper<>("Couldn't register", null)));
                }
            }else {
                return notFound(JsonServiceUtil.toJsonNode(new ResponseWrapper<>("Invalid User", null)));
            }
        } catch (JsonProcessingException e) {
            Logger.error(e.getMessage());
            return badRequest(JsonServiceUtil.toJsonNode(new ResponseWrapper<>("Can not get data", null)));
        }
    }
    /**
     * Getting the Json which comes from the front end and assiging it to a LoginCredential object and passing it to the Service
     * @return Doing all the validations and returning a json with the specific status code
     */
    @With(UserAuthentication.class)
    public Result logoutUser(){
        User loggedInUser = (User) ctx().args.get("user");
        loggedInUser.setAuthToken("");
        if (loggedInUser.getAuthToken().equals("")) {
           User logoutedUser = userService.logout(loggedInUser);
            return ok(JsonServiceUtil.toJsonNode(new ResponseWrapper<>("Successfully Logged Out", logoutedUser)));
        }else {
            return badRequest(JsonServiceUtil.toJsonNode(new ResponseWrapper<>("Invalid User", null)));
        }
    }
    /**
     * Getting the Json which comes from the front end and assiging it to a LoginCredential object and passing it to the Service
     * @return Doing all the validations and returning a json with the specific status code
     */
    @With(UserAuthentication.class)
    public Result deleteUser(){
        User loggedInUser = (User) ctx().args.get("user");
        if (loggedInUser != null) {
            User deletedUser = userService.deleteUser(loggedInUser);
            return ok(JsonServiceUtil.toJsonNode(new ResponseWrapper<>("Successfully Logged Out", deletedUser)));
        }else {
            return badRequest(JsonServiceUtil.toJsonNode(new ResponseWrapper<>("Invalid User", null)));
        }
    }
}
