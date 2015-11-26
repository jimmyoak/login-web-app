package main.java.Domain.User;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class Password  implements Serializable {
    private final static String PASSWORD_ENCODING = "UTF-8";
    private boolean crypted;
    private byte[] password;
    
    private Password(byte[] password, boolean crypted) {
        this.password = password;
        this.crypted = crypted;
    }
    
    public static Password createFromCrypted(byte[] password){
        return new Password(password, true);
    }
    
    public static Password createFromUncrypted(byte[] password) {
        return new Password(password, false);
    }
    
    public static Password createFromUncrypted(String password) throws UnsupportedEncodingException {
        return createFromUncrypted(password.getBytes(PASSWORD_ENCODING));
    }
    
    public boolean isCrypted()
    {
        return this.crypted;
    }
    
    public byte[] getPassword()
    {
        return this.password;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Password other = (Password) obj;
        if (this.crypted != other.crypted) {
            return false;
        }
        if (!Arrays.equals(this.password, other.password)) {
            return false;
        }
        return true;
    }
    
    
}
