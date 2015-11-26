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
        
        Password defaultPassword = Password.createFromUncrypted("12345");
        
        defaultPassword = (new PasswordCrypter()).cryptPassword(defaultPassword);

        this.persist(new User("jimmykoak", defaultPassword, createRoleWithPermissions("PAGE_1")));
        this.persist(new User("adrian.robles.maiz", defaultPassword, createRoleWithPermissions("PAGE_2")));
        this.persist(new User("someone", defaultPassword, createRoleWithPermissions("PAGE_3")));
    }

    private Role createRoleWithPermissions(String rolename) {
        HashSet<RolePermission> permissions = new HashSet<>();
        permissions.add(new RolePermission(rolename));
        return new Role(rolename, permissions);
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
