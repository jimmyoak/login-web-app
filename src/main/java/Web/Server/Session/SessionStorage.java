package main.java.Web.Server.Session;

import main.java.Domain.User.RolePermission;
import main.java.Domain.User.User;

import java.io.Serializable;
import java.util.UUID;

public class SessionStorage implements Serializable {
    private String id;

    private User user;

    public SessionStorage() {
        id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public boolean hasUser() {
        return this.user != null;
    }

    public boolean hasPermissionTo(String permission) {
        return hasUser() && getUser().hasPermission(new RolePermission(permission));
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void logoutUser() {
        this.user = null;
    }
}
