package test.java.Domain.User;

import junit.framework.TestCase;
import main.java.Domain.User.Password;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class PasswordTest extends TestCase {
    public void testShouldCreatePasswordByDifferentMethods() throws UnsupportedEncodingException {
        String password = "12345";
        byte[] passwordInBytes = password.getBytes("UTF-8");

        Password aPassword = Password.createFromUncrypted(password);
        Password anotherPassword = Password.createFromUncrypted(passwordInBytes);

        assertFalse(aPassword.isCrypted());
        assertFalse(anotherPassword.isCrypted());

        assertTrue(aPassword.equals(anotherPassword));
        assertTrue(Arrays.equals(passwordInBytes, anotherPassword.getPassword()));
        assertTrue(Arrays.equals(passwordInBytes, aPassword.getPassword()));
    }

    public void testShouldCreateCryptedPassword() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        byte[] cryptedPasswordBytes = {-126, 124, -53, 14, -22, -118, 112, 108, 76, 52, -95, 104, -111, -8, 78, 123};
        Password password = Password.createFromCrypted(cryptedPasswordBytes);

        assertTrue(password.isCrypted());
        assertEquals(cryptedPasswordBytes, password.getPassword());
    }
}
