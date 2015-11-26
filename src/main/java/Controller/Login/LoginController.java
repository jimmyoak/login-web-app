package main.java.Controller.Login;

import main.java.Controller.ControllerBase;

import java.io.IOException;

public class LoginController extends ControllerBase {
    public void get() throws IOException {
        sendSuccesfulResponse("OK");
    }
}
