package main.java.Web.Controller.Login;

import main.java.Web.Controller.ControllerBase;

import java.io.IOException;

public class LogoutController extends ControllerBase {
    public void get() throws IOException {
        getSessionStorage().logoutUser();
        redirectTemporarly("/");
    }
}
