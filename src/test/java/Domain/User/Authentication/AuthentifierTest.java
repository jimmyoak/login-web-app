package test.java.Domain.User.Authentication;

import junit.framework.TestCase;
import main.java.Domain.User.Authentication.Authentifier;
import main.java.Domain.User.Password;
import main.java.Domain.User.User;
import main.java.Infrastructure.Persistence.User.InMemoryUserRepository;

import java.io.UnsupportedEncodingException;

public class AuthentifierTest extends TestCase {
    private Authentifier authentifier;

    public static final String VALID_USERNAME = "jimmykoak";
    public static final String VALID_PASSWORD = "12345";
    private static final String INVALID_USERNAME = "invalid_username";
    private static final String INVALID_PASSWORD = "invalid_password";

    @Override
    protected void setUp() throws Exception {
        authentifier = new Authentifier(
                new InMemoryUserRepository()
        );
    }

    public void testShouldAuthenticateValidUser() throws UnsupportedEncodingException {
        User user = authentifier.authenticate(VALID_USERNAME, Password.createFromUncrypted(VALID_PASSWORD));

        assertNotNull(user);
        assertSame(VALID_USERNAME, user.getUsername());
    }

    public void testShouldNotAuthenticateInvalidUser() throws UnsupportedEncodingException {
        User user = authentifier.authenticate(INVALID_USERNAME, Password.createFromUncrypted(VALID_PASSWORD));

        assertNull(user);
    }

    public void testShouldNotAuthenticateValidUserButInvalidPassword() throws UnsupportedEncodingException {
        User user = authentifier.authenticate(VALID_USERNAME, Password.createFromUncrypted(INVALID_PASSWORD));

        assertNull(user);
    }
}
