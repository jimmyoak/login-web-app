package main.java.Web.Controller.Login;

import main.java.Interactor.Authentication.AuthenticateUser;
import main.java.Interactor.Authentication.AuthenticateUserRequest;
import main.java.Interactor.Authentication.AuthenticateUserResponse;
import main.java.Web.Controller.ControllerBase;

import java.io.IOException;
import java.util.HashMap;

public class LoginController extends ControllerBase {
    public void get() throws IOException {
        HashMap<String, String> parameters = getUriParameters();

        HashMap<String, String> templateVars = new HashMap<>();
        if (parameters.get("error") != null) {
            templateVars.put("ERROR", parameters.get("error"));
        }

        sendSuccessfulResponse(render("/main/java/Web/View/Login/login.html", templateVars));
    }

    public void post() throws IOException {
        HashMap<String, String> bodyParams = getRequestBodyParameters();

        AuthenticateUserResponse authenticateUserResponse = new AuthenticateUser().execute(new AuthenticateUserRequest(
                bodyParams.get("username"),
                bodyParams.get("password")
        ));

        getSessionStorage().setUser(authenticateUserResponse.getUser());

        if (authenticateUserResponse.isAuthenticated()) {
            redirectTemporarly("/");
        } else {
            redirectTemporarly("/login?error=Invalid%20Credentials");
        }
    }
}
