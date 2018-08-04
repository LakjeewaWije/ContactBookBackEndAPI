package authentication;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import dao.UserDao;
import models.User;
import play.libs.F;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;
import utils.JsonServiceUtil;
import utils.ResponseWrapper;

import java.util.concurrent.CompletionStage;

/**
 * Created by lakjeewa on 8/1/18.
 */
public class UserAuthentication  extends Action.Simple {

    @Inject
    @Named("userDao")
    private UserDao userDao;

    /**
     * Gets the token that user sent to the service which attached on header.
     * In this case, User must be sent the X-AUTH_TOKEN key to authenticate
     *
     * @param ctx the http context in which to execute this action
     * @return an array with all auth token headers
     */
    private String getTokenFromHeader(Http.Context ctx) {
        String[] authTokenHeaderValues = ctx.request().headers().get("authToken");
        if ((authTokenHeaderValues != null) && (authTokenHeaderValues.length == 1) && (authTokenHeaderValues[0] != null)) {
            return authTokenHeaderValues[0];
        }
        return null;
    }

    public CompletionStage<Result> call(Http.Context ctx) {
        String token = getTokenFromHeader(ctx);
        Result unauthorized = Results.unauthorized(JsonServiceUtil.toJsonNode(
                new ResponseWrapper<>("Unauthorized to access", "This request have no enough access details")));
        if (token != null) {
            User user = userDao.findUserByToken(token);
            if (user == null) {
                return F.Promise.pure(unauthorized);
            }
            if (user != null) {
                ctx.args.put("user", user);
                return delegate.call(ctx);
            }
        }

        return F.Promise.pure(unauthorized);
    }
}
