package main.java.Controller;

import java.io.IOException;
import java.net.HttpCookie;

public class CookieController extends ControllerBase {
    public void get() throws IOException {
        String response = "";

        for (HttpCookie cookie : getCookies()) {
            response += cookie.toString();
        }

        sendSuccesfulResponse(response);
    }
}
