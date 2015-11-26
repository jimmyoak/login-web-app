package main.java.Domain.User;

import java.io.Serializable;

public class RolePermission implements Serializable {
    private String permission;

    public RolePermission(String permission) {
        this.permission = permission.toUpperCase();
    }

    @Override
    public String toString() {
        return this.permission;
    }

    public boolean equals(RolePermission rolePermission) {
        return this.permission.equals(rolePermission.permission);
    }
}
