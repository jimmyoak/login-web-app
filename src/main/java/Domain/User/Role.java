package main.java.Domain.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Role  implements Serializable {
    private String name;

    private Set<RolePermission> permissions = new HashSet<>();

    public Role(String name, Set<RolePermission> permissions) {
        this.name = name;
        this.permissions = permissions;
    }

    public String getName() {
        return name;
    }

    public boolean hasPermission(RolePermission rolePermission) {
        for (RolePermission permission:permissions) {
            if (permission.equals(rolePermission)) {
                return true;
            }
        }

        return false;
    }

    public ArrayList<RolePermission> getPermissions() {
        ArrayList<RolePermission> permissions = new ArrayList<>();
        for (RolePermission permission:this.permissions) {
            permissions.add(permission);
        }

        return permissions;
    }
}
