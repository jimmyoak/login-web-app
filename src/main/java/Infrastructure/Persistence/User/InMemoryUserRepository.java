package main.java.Infrastructure.Persistence.User;

import main.java.Domain.User.*;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashSet;

public final class InMemoryUserRepository implements UserRepository
{
    private final ArrayList<User> users;
    
    public InMemoryUserRepository()
            throws NoSuchAlgorithmException, UnsupportedEncodingException
    {
        this.users = new ArrayList<>();

        initializeUsers();
    }

    private void initializeUsers() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Password defaultPassword = Password.createFromUncrypted("12345");

        defaultPassword = (new PasswordCrypter()).cryptPassword(defaultPassword);

        this.persist(
                new User("jimmykoak", defaultPassword, createRoleWithPermissions("Page 1", "PAGE_1"))
        );
        this.persist(
                new User("adrian.robles.maiz", defaultPassword, createRoleWithPermissions("Page 2", "PAGE_2"))
        );
        this.persist(
                new User("someone", defaultPassword, createRoleWithPermissions("Page 3", "PAGE_3"))
        );
        this.persist(
                new User("awesome_user", defaultPassword, createRoleWithPermissions("Admin", "PAGE_1", "PAGE_3"))
        );
        this.persist(
                new User("root", defaultPassword, createRoleWithPermissions("root", "PAGE_1", "PAGE_2", "PAGE_3"))
        );
    }

    private Role createRoleWithPermissions(String rolename, String... permissions) {
        HashSet<RolePermission> rolePermissions = new HashSet<>();
        for (String permission:permissions) {
            rolePermissions.add(new RolePermission(permission));
        }

        return new Role(rolename, rolePermissions);
    }

    @Override
    public void persist(User user)
    {
        this.users.add(user);
    }

    @Override
    public User findById(String id)
    {
        for (User user : this.users) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        
        return null;
    }

    @Override
    public User[] findAll()
    {
        return (User[]) this.users.toArray();
    }

    @Override
    public User findByUsername(String username) {
        for (User user : this.users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        
        return null;
    }
}
