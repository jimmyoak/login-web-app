/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.Interactor.Authentication;

/**
 *
 * @author adrian.robles
 */
public class AuthenticateUserRequest {
    private String username;
    
    private byte[] password;
    
    public AuthenticateUserRequest(String username, String password)
    {
        this.username = username;
        this.password = password.getBytes();
    }
    
    public String getUsername()
    {
        return this.username;
    }
    
    public byte[] getPassword()
    {
        return this.password;
    }
}
