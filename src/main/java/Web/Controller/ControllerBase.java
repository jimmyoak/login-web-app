package main.java.Web.Controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import main.java.Web.Server.Cookie.Cookies;
import main.java.Web.Server.Session.FileSessionHandler;
import main.java.Web.Server.Session.SessionCreationException;
import main.java.Web.Server.Session.SessionHandler;
import main.java.Web.Server.Session.SessionStorage;
import sun.misc.Regexp;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.BiConsumer;
import java.util.regex.Pattern;

abstract public class ControllerBase implements HttpHandler {
    private HttpExchange httpExchange;
    private Cookies cookies;
    private SessionHandler sessionHandler;
    private HashMap<String, String> templateVars;

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

        initializeTemplateVars();

        String requestMethod = httpExchange.getRequestMethod().toLowerCase();
        try {
            Method method = this.getClass().getDeclaredMethod(requestMethod);
            method.invoke(this);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();

            sendNotFoundResponse();
        }
    }

    private void initializeTemplateVars() {
        this.templateVars = new HashMap<>();
        if (getSessionStorage().hasUser()) {
            templateVars.put("USERNAME", getSessionStorage().getUser().getUsername());
        }
    }

    protected HashMap<String, String> getUriParameters() {
        String queryString = httpExchange.getRequestURI().getQuery();
        if (queryString == null) {
            queryString = "";
        }

        return parseQueryString(queryString);
    }

    protected HashMap<String, String> getRequestBodyParameters() {
        InputStream requestBody = getHttpExchange().getRequestBody();
        Scanner scanner = new Scanner(requestBody).useDelimiter("\\A");
        String queryString = scanner.next();

        return parseQueryString(queryString);
    }

    private HashMap<String, String> parseQueryString(String queryString) {
        HashMap<String, String> parameters = new HashMap<>();

        String[] queryParams = queryString.split("&");
        for (String queryParam : queryParams) {
            int firstEqualPosition = queryParam.indexOf('=');
            if (firstEqualPosition == -1) {
                parameters.put(queryParam, null);
            } else {
                String queryParamName = queryParam.substring(0, firstEqualPosition);
                String queryParamValue = null;
                try {
                    queryParamValue = URLDecoder.decode(queryParam.substring(firstEqualPosition + 1), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                parameters.put(queryParamName, queryParamValue);
            }
        }

        return parameters;
    }

    private void sendResponse(int httpStatusCode, String responseBody) throws IOException {
        sessionHandler.close();
        if (!cookies.isEmpty()) {
            for (String cookieHeaderValue : cookies.getCookieHeaderValues()) {
                httpExchange.getResponseHeaders().add("Set-Cookie", cookieHeaderValue);
            }
        }
        httpExchange.sendResponseHeaders(httpStatusCode, responseBody.getBytes().length);
        httpExchange.getResponseBody().write(responseBody.getBytes());
        httpExchange.close();
    }

    protected void sendSuccessfulResponse(String responseBody) throws IOException {
        sendResponse(200, responseBody);
    }

    protected void sendUnauthorizedResponse(String responseBody) throws IOException {
        sendResponse(401, responseBody);
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
        return render(viewPath, templateVars);
    }

    protected String render(String viewPath, HashMap<String, String> vars) {
        templateVars.putAll(vars);

        InputStream resource = getClass().getResourceAsStream(viewPath);
        Scanner scanner = new Scanner(resource).useDelimiter("\\A");

        String templateContent = scanner.next();
        for (Map.Entry<String, String> var : templateVars.entrySet()) {
            templateContent = templateContent.replaceAll(
                    Pattern.quote("{{ " + var.getKey() + " }}"),
                    var.getValue()
            );
        }
        //Replace all non replaced vars
        templateContent = templateContent.replaceAll("\\{\\{ \\w+ \\}\\}", "");

        return templateContent;
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
