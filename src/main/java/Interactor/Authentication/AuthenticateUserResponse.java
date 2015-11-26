/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.Interactor.Authentication;

import main.java.Domain.User.User;

/**
 *
 * @author adrian.robles
 */
public class AuthenticateUserResponse{
    private boolean authenticated = false;
    
    private User user;
    
    public AuthenticateUserResponse(User user)
    {
        if (user != null) {
            this.authenticated = true;
            this.user = user;
        }
    }
    
    public boolean isAuthenticated()
    {
        return this.authenticated;
    }
    
    public User getUser()
    {
        return this.user;
    }
}
