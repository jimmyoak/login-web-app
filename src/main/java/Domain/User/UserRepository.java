package main.java.Domain.User;

/**
 *
 * @author adrian.robles
 */
public interface UserRepository
{
    public void persist(User user);
    
    public User findById(String id);
    
    public User findByUsername(String username);
    
    public User[] findAll();
}
