package main.java.Controller.Home;

import main.java.Controller.ControllerBase;

import java.io.IOException;

public class HomeController extends ControllerBase {
    public void get() throws IOException {
        String response = render("/main/java/View/Home/main.html");

        sendSuccesfulResponse(response);
    }
}
