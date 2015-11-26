package test.java.Domain.User;

import junit.framework.TestCase;
import main.java.Domain.User.RolePermission;

public class RolePermissionTest extends TestCase {
    public static final String EDIT_ROLE_PERMISSION = "EDIT";
    public static final String VIEW_ROLE_PERMISSION = "VIEW";
    private RolePermission rolePermission;

    @Override
    protected void setUp() throws Exception {
        rolePermission = new RolePermission(EDIT_ROLE_PERMISSION);
    }

    public void testShouldGetRolePermissionName() {
        assertSame(EDIT_ROLE_PERMISSION, rolePermission.toString());
    }

    public void testShouldEqualsOtherRolePermissions() {
        assertFalse(new RolePermission(VIEW_ROLE_PERMISSION).equals(rolePermission));
        assertTrue(new RolePermission(EDIT_ROLE_PERMISSION).equals(rolePermission));
    }
}
