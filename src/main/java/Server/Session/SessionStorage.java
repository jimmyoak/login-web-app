package main.java.Server.Session;

import java.io.Serializable;
import java.util.UUID;

public class SessionStorage implements Serializable {
    private String id;

    public SessionStorage() {
        id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }
}
