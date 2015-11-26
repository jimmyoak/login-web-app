package main.java.Domain.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

public class User implements Serializable{
    private String id;
    
    private String username;
    
    private Password password;

    private Role role;
    
    public User(String username, Password password, Role role)
    {
        this.id = UUID.randomUUID().toString();
        this.username = username;
        this.password = password;
        this.role = role;
    }
    
    public String getId()
    {
        return this.id;
    }
    
    public String getUsername()
    {
        return this.username;
    }
    
    public Password getPassword()
    {
        return this.password;
    }

    public String getRoleName() {
        return this.role.getName();
    }

    public boolean hasPermission(RolePermission permission) {
        return this.role.hasPermission(permission);
    }

    public ArrayList<RolePermission> getPermissions() {
        return this.role.getPermissions();
    }
}
