package main.java.Web.Server.Session;

import main.java.Web.Server.Cookie.Cookies;

import java.io.*;
import java.net.HttpCookie;

public class FileSessionHandler extends SessionHandler {
    private Cookies cookies;
    private String sessionStorageDir;

    public FileSessionHandler(Cookies cookies) throws SessionCreationException {
        this.cookies = cookies;

        sessionStorageDir = System.getProperty("user.dir") + "/sessions";

        File sessionFolder = new File(sessionStorageDir);
        if (!sessionFolder.exists()) {
            if (!sessionFolder.mkdir()) {
                throw new SessionCreationException("An error ocurred while creating sessions folder");
            }
        }
    }

    public void start() throws SessionCreationException {
        if (this.sessionStorage == null) {
            SessionStorage cookieSession = null;
            try {
                cookieSession = getSessionFromCookies();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            if (cookieSession != null) {
                this.sessionStorage = cookieSession;
            } else {
                SessionStorage sessionStorage = new SessionStorage();
                HttpCookie sessionId = new HttpCookie("SESSION_ID", sessionStorage.getId());
                sessionId.setMaxAge(5);
                cookies.set(sessionId);

                this.sessionStorage = sessionStorage;
            }
        }
    }

    public void close() {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                    new FileOutputStream(getSessionStoragePath(sessionStorage.getId()))
            );
            objectOutputStream.writeObject(sessionStorage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getSessionStoragePath(String sessionId) {
        return sessionStorageDir + "/" + sessionId;
    }

    private SessionStorage getSessionFromCookies() throws ClassNotFoundException {
        HttpCookie sessionId = cookies.get("SESSION_ID");

        if (sessionId != null) {
            String sessionIdValue = sessionId.getValue();
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(
                        new FileInputStream(getSessionStoragePath(sessionIdValue))
                );

                return (SessionStorage) objectInputStream.readObject();
            } catch (IOException e) {
                // In case session file is not found, will create a new one
                System.err.println("Session file (" + sessionIdValue + ") not found");
                e.printStackTrace();
            }
        }

        return null;
    }
}
