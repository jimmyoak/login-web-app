package main.java.Domain.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordCrypter {
    private static final String CRYPTER_ALGORITHM = "MD5";
    private final MessageDigest crypter;
    
    public PasswordCrypter() throws NoSuchAlgorithmException {
        this.crypter = MessageDigest.getInstance(CRYPTER_ALGORITHM);
    }
    
    public Password cryptPassword(Password password) {
        if (!password.isCrypted()) {
            password = Password.createFromCrypted(
                    crypter.digest(password.getPassword())
            );
        }
        
        return password;
    }
}
