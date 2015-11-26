package test.java.Interactor.Authentication;

import junit.framework.TestCase;
import main.java.Interactor.Authentication.AuthenticateUser;
import main.java.Interactor.Authentication.AuthenticateUserRequest;
import main.java.Interactor.Authentication.AuthenticateUserResponse;

public class AuthenticateUserTest extends TestCase {

    public static final String VALID_USERNAME = "jimmykoak";
    public static final String VALID_PASSWORD = "12345";
    private static final String INVALID_USERNAME = "invalid_username";
    private static final String INVALID_PASSWORD = "invalid_password";

    public void testShouldAuthenticateValidUser()
    {
        AuthenticateUserResponse response = new AuthenticateUser().execute(
                new AuthenticateUserRequest(VALID_USERNAME, VALID_PASSWORD)
        );

        assertTrue(response.isAuthenticated());
        assertNotNull(response.getUser());
        assertSame(VALID_USERNAME, response.getUser().getUsername());
    }

    public void testShouldNotAuthenticateInvalidUser()
    {
        AuthenticateUserResponse response = new AuthenticateUser().execute(
                new AuthenticateUserRequest(INVALID_USERNAME, VALID_PASSWORD)
        );

        assertFalse(response.isAuthenticated());
        assertNull(response.getUser());
    }

    public void testShouldNotAuthenticateValidUserButInvalidPassword()
    {
        AuthenticateUserResponse response = new AuthenticateUser().execute(
                new AuthenticateUserRequest(VALID_USERNAME, INVALID_PASSWORD)
        );

        assertFalse(response.isAuthenticated());
        assertNull(response.getUser());
    }
}
