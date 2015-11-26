package test.java.Domain.User;

import junit.framework.TestCase;
import main.java.Domain.User.Role;
import main.java.Domain.User.RolePermission;

import java.util.HashSet;

public class RoleTest extends TestCase {
    public static final String ROLE_NAME = "ROLE_NAME";
    public static final String WRITE = "WRITE";
    private static final String READ = "READ";
    private Role role;
    private HashSet<RolePermission> permissions;

    @Override
    protected void setUp() throws Exception {
        permissions = new HashSet<>();
        permissions.add(new RolePermission(WRITE));
        role = new Role(ROLE_NAME, permissions);
    }

    public void testShouldGetRoleName() {
        assertSame(ROLE_NAME, role.getName());
    }

    public void testShouldHasRolePermission() {
        assertTrue(role.hasPermission(new RolePermission(WRITE)));
        assertFalse(role.hasPermission(new RolePermission(READ)));
    }

    public void testShouldGetPermissions() {
        assertSame(permissions.size(), role.getPermissions().size());
    }
}
