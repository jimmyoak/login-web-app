package main.java.Controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import main.java.Server.Cookie.Cookies;
import main.java.Server.Session.FileSessionHandler;
import main.java.Server.Session.SessionCreationException;
import main.java.Server.Session.SessionHandler;
import main.java.Server.Session.SessionStorage;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;

abstract public class ControllerBase implements HttpHandler {
    private HttpExchange httpExchange;
    private Cookies cookies;
    private SessionHandler sessionHandler;

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        this.httpExchange = httpExchange;
        this.cookies = new Cookies(httpExchange.getRequestHeaders());

        try {
            this.sessionHandler = new FileSessionHandler(cookies);
            this.sessionHandler.start();
        } catch (SessionCreationException e) {
            e.printStackTrace();
            return;
        }

        String requestMethod = httpExchange.getRequestMethod().toLowerCase();
        try {
            Method method = this.getClass().getDeclaredMethod(requestMethod);
            method.invoke(this);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();

            sendNotFoundResponse();
        }
    }

    private void sendResponse(int httpStatusCode, String responseBody) throws IOException {
        sessionHandler.close();
        if (!cookies.isEmpty()) {
            httpExchange.getResponseHeaders().set("Set-Cookie", cookies.toString());
        }
        httpExchange.sendResponseHeaders(httpStatusCode, responseBody.getBytes().length);
        httpExchange.getResponseBody().write(responseBody.getBytes());
        httpExchange.close();
    }

    protected void sendSuccesfulResponse(String responseBody) throws IOException {
        sendResponse(200, responseBody);
    }

    protected void sendNotFoundResponse() throws IOException {
        sendResponse(404, "404 Not Found");
    }

    protected void sendServerError() throws IOException {
        sendResponse(503, "503 Server Error");
    }

    protected void redirectTemporarly(String path) throws IOException {
        httpExchange.getResponseHeaders().add("Location", path);
        sendResponse(302, "");
    }

    protected String render(String viewPath) {
        InputStream resource = getClass().getResourceAsStream(viewPath);
        Scanner scanner = new Scanner(resource).useDelimiter("\\A");

        return scanner.next();
    }

    protected HttpExchange getHttpExchange() {
        return httpExchange;
    }

    protected Cookies getCookies() {
        return cookies;
    }

    protected SessionStorage getSessionStorage() {
        return sessionHandler.getSessionStorage();
    }
}
