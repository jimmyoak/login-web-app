package main.java.Web.Controller.Home;

import main.java.Web.Controller.ControllerBase;

import java.io.IOException;

public class HomeController extends ControllerBase {
    public void get() throws IOException {
        sendSuccessfulResponse(render("/main/java/Web/View/Home/main.html"));
    }
}
