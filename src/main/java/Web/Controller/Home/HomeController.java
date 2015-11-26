package main.java.Web.Controller.Home;

import main.java.Web.Controller.ControllerBase;

import java.io.IOException;
import java.util.HashMap;

public class HomeController extends ControllerBase {
    public void get() throws IOException {
        HashMap<String, String> templateVars = new HashMap<>();

        if (getSessionStorage().hasUser()) {
            templateVars.put("USERNAME", getSessionStorage().getUser().getUsername());
        }

        String response = render("/main/java/Web/View/Home/main.html", templateVars);

        sendSuccessfulResponse(response);
    }
}
