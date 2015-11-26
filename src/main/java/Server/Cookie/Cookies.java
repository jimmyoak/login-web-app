package main.java.Server.Cookie;

import com.sun.net.httpserver.Headers;

import java.net.CookieManager;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Cookies implements Iterable<HttpCookie> {
    public static final String COOKIE_HEADER_NAME = "Cookie";
    private HashMap<String, HttpCookie> cookies = new HashMap<>();

    public Cookies(Headers headers) {
        String cookieHeader = headers.getFirst(COOKIE_HEADER_NAME);
        if (cookieHeader != null) {
            List<HttpCookie> cookiesList = HttpCookie.parse(cookieHeader);
            for (HttpCookie cookie : cookiesList) {
                cookies.put(cookie.getName(), cookie);
            }
        }
    }

    @Override
    public String toString() {
        String header = "";
        for (HttpCookie cookie : cookies.values()) {
            header += cookie;
        }

        return header;
    }

    public HttpCookie get(String cookieName) {
        return cookies.get(cookieName);
    }

    public void set(HttpCookie httpCookie) {
        cookies.put(httpCookie.getName(), httpCookie);
    }

    public boolean isEmpty() {
        return cookies.isEmpty();
    }

    @Override
    public Iterator<HttpCookie> iterator() {
        return cookies.values().iterator();
    }
}
