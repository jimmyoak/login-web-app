package main.java.Web.Controller.Page;

import main.java.Web.Controller.ControllerBase;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;

public class PageController extends ControllerBase {
    public void get() throws IOException {
        HashMap<String, String> parameters = getUriParameters();

        HashMap<String, String> templateVars = new HashMap<>();
        String pageName = parameters.get("pg");
        if (pageName == null) {
            sendNotFoundResponse();
        } else if (getSessionStorage().hasPermissionTo(pageName)) {
            templateVars.put("PAGE_NAME", pageName);

            sendSuccessfulResponse(render("/main/java/Web/View/Page/page.html", templateVars));
        } else {
            templateVars.put("LOGIN_URL", "/login?error=" + URLEncoder.encode("You don't have permissions to see this page", "UTF-8"));
            sendUnauthorizedResponse(render("/main/java/Web/View/Page/unathorized.html", templateVars));
        }
    }
}
