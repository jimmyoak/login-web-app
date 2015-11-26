package main.java.Domain.User.Authentication;

import main.java.Domain.User.Password;
import main.java.Domain.User.PasswordCrypter;
import main.java.Domain.User.User;
import main.java.Domain.User.UserRepository;

import java.security.NoSuchAlgorithmException;

public class Authentifier {
    private final UserRepository userRepository;
    private final PasswordCrypter passwordCrypter;

    public Authentifier(UserRepository userRepository)
            throws NoSuchAlgorithmException {
        this.userRepository = userRepository;
        this.passwordCrypter = new PasswordCrypter();
    }
    
    public User authenticate(String username, Password password)
    {
        User user = this.userRepository.findByUsername(username);
        if (user != null) {
            Password cryptedPassword = passwordCrypter.cryptPassword(password);
            if (cryptedPassword.equals(user.getPassword())) {
                return user;
            }
        }
        
        return null;
    }
}
