/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.Interactor.Authentication;

import main.java.Domain.User.Authentication.Authentifier;
import main.java.Domain.User.Password;
import main.java.Domain.User.User;
import main.java.Infrastructure.Persistence.User.InMemoryUserRepository;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author adrian.robles
 */
public class AuthenticateUser {
    public AuthenticateUserResponse execute(AuthenticateUserRequest request) {
        try {
            Authentifier authentifier = new Authentifier(
                    new InMemoryUserRepository()
            );
            
            User user = authentifier.authenticate(
                    request.getUsername(),
                    Password.createFromUncrypted(request.getPassword())
            );
            
            return new AuthenticateUserResponse(user);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(AuthenticateUser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(AuthenticateUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return new AuthenticateUserResponse(null);
    }
}
