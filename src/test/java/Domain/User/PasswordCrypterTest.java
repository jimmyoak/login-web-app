package test.java.Domain.User;

import junit.framework.TestCase;
import main.java.Domain.User.Password;
import main.java.Domain.User.PasswordCrypter;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class PasswordCrypterTest extends TestCase {
    public static final String PASSWORD = "12345";
    public static final byte[] CRYPTED_PASSWORD_BYTES = new byte[]{-126, 124, -53, 14, -22, -118, 112, 108, 76, 52, -95, 104, -111, -8, 78, 123};

    private PasswordCrypter passwordCrypter;

    @Override
    protected void setUp() throws Exception {
        passwordCrypter = new PasswordCrypter();
    }

    public void testShouldEncryptPassword() throws UnsupportedEncodingException {
        Password cryptedPassword =  passwordCrypter.cryptPassword(Password.createFromUncrypted(PASSWORD));

        assertTrue(cryptedPassword.isCrypted());
        assertTrue(Arrays.equals(CRYPTED_PASSWORD_BYTES, cryptedPassword.getPassword()));
    }

    public void testShouldReturnSamePasswordIfAlreadyEncrypted() {
        Password alreadyCryptedPassword = Password.createFromCrypted(CRYPTED_PASSWORD_BYTES);

        Password cryptedPassword = passwordCrypter.cryptPassword(alreadyCryptedPassword);

        assertSame(cryptedPassword, alreadyCryptedPassword);
    }
}
