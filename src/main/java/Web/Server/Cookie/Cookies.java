package main.java.Web.Server.Cookie;

import com.sun.net.httpserver.Headers;

import java.net.HttpCookie;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

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

    public ArrayList<String> getCookieHeaderValues()
    {
        ArrayList<String> cookieHeaders = new ArrayList<>();
        for (HttpCookie cookie:this) {
            cookieHeaders.add(stringifyCookie(cookie));
        }

        return cookieHeaders;
    }

    private String stringifyCookie(HttpCookie cookie) {
        StringBuilder sb = new StringBuilder();

        sb.append(cookie.getName()).append("=").append(cookie.getValue());
        if (cookie.getMaxAge() >= 0) {
            Date expirationDate = new Date();
            DateFormat cookieFormat = new SimpleDateFormat("EEE, dd-MMM-yyyy HH:mm:ss zzz", Locale.ENGLISH);
            cookieFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            expirationDate.setTime(expirationDate.getTime() + (cookie.getMaxAge() * 1000));

            sb.append("; expires=").append(cookieFormat.format(expirationDate));
            sb.append("; Max-Age=").append(cookie.getMaxAge());
        }

        return sb.toString();
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
