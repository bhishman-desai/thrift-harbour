package tech.group15.thriftharbour.service.implementation;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import tech.group15.thriftharbour.dto.*;
import tech.group15.thriftharbour.dto.request.*;
import tech.group15.thriftharbour.dto.response.ForgotPassResponse;
import tech.group15.thriftharbour.dto.response.SignInResponse;
import tech.group15.thriftharbour.exception.EmailAlreadyExistsException;
import tech.group15.thriftharbour.mapper.UserMapper;
import tech.group15.thriftharbour.model.PasswordResetToken;
import tech.group15.thriftharbour.model.User;
import tech.group15.thriftharbour.repository.PasswordResetTokenRepository;
import tech.group15.thriftharbour.repository.UserRepository;
import tech.group15.thriftharbour.service.EmailService;
import tech.group15.thriftharbour.service.JWTService;
import static org.mockito.ArgumentMatchers.*;
@RunWith(MockitoJUnitRunner.class)
public class AuthenticationServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JWTService jwtService;

    @Mock
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    @Before
    public void setup() {
        // Mocking behavior for dependencies
    }

    @Test
    public void testSignUp() {
        // Create a SignUpRequest object
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setEmail("test@example.com");
        signUpRequest.setPassword("password");

        // Mock userRepository.existsByEmail() to return false
        when(userRepository.existsByEmail(signUpRequest.getEmail())).thenReturn(false);

        // Mock userRepository.save() to return a User object
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User savedUser = invocation.getArgument(0);
            //savedUser.setId(1); // Simulate saving with an ID
            return savedUser;
        });

        // Call the signUp method
        User result = authenticationService.signUp(signUpRequest);

        // Verify that the result is not null
        assertNotNull(result);

        // Verify that the email of the returned user matches the email in the signUpRequest
        assertEquals(signUpRequest.getEmail(), result.getEmail());
    }


    @Test(expected = EmailAlreadyExistsException.class)
    public void testSignUp_EmailAlreadyExists() {
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setEmail("existing@example.com");
        signUpRequest.setPassword("password");

        // Mock userRepository.existsByEmail()
        when(userRepository.existsByEmail(signUpRequest.getEmail())).thenReturn(true);

        authenticationService.signUp(signUpRequest);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testSignIn_InvalidEmailOrPassword() {
        SignInRequest signInRequest = new SignInRequest();
        signInRequest.setEmail("invalid@example.com");
        signInRequest.setPassword("invalid");

        // Mock userRepository.findByEmail()
        when(userRepository.findByEmail(signInRequest.getEmail())).thenReturn(Optional.empty());

        authenticationService.signIn(signInRequest);
    }

    @Test
    public void testResetPassTokenVerify_ValidToken() {
        String token = "reset_token";

        PasswordResetToken passResetToken = new PasswordResetToken();
        passResetToken.setToken(token);
        passResetToken.setExpiryDate(new Date(System.currentTimeMillis() + 1000 * 60 * 30)); // Set expiry to future time

        // Mock passwordResetTokenRepository.findByToken()
        when(passwordResetTokenRepository.findByToken(token)).thenReturn(Optional.of(passResetToken));

        Object result = authenticationService.resetPassTokenVerify(token);

        assertEquals("http://172.17.1.50:3000/reset-password?token=" + token, result);
    }

    @Test
    public void testResetPassTokenVerify_ExpiredToken() {
        String token = "expired_token";

        PasswordResetToken passResetToken = new PasswordResetToken();
        passResetToken.setToken(token);
        passResetToken.setExpiryDate(new Date(System.currentTimeMillis() - 1000 * 60 * 30)); // Set expiry to past time

        // Mock passwordResetTokenRepository.findByToken()
        when(passwordResetTokenRepository.findByToken(token)).thenReturn(Optional.of(passResetToken));

        Object result = authenticationService.resetPassTokenVerify(token);

        assertEquals("http://172.17.1.50:3000/login?msg=Link Expired", result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testResetPassTokenVerify_InvalidToken() {
        String token = "invalid_token";

        // Mock passwordResetTokenRepository.findByToken()
        when(passwordResetTokenRepository.findByToken(token)).thenReturn(Optional.empty());

        authenticationService.resetPassTokenVerify(token);
    }

    @Test
    public void testResetPassword() {
        ResetPassRequest resetPassRequest = new ResetPassRequest();
        resetPassRequest.setToken("reset_token");
        resetPassRequest.setPassword("new_password");

        User user = new User();
        //user.setUserID(UUID.randomUUID());

        PasswordResetToken passResetToken = new PasswordResetToken();
        passResetToken.setToken(resetPassRequest.getToken());
        passResetToken.setUser(user);

        // Mock passwordResetTokenRepository.findByToken()
        when(passwordResetTokenRepository.findByToken(resetPassRequest.getToken())).thenReturn(Optional.of(passResetToken));

        // Mock userRepository.findById() and userRepository.save()
        when(userRepository.findById(user.getUserID())).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Object result = authenticationService.resetPassword(resetPassRequest);

        assertEquals("Password Changed Successfully", result);
    }
    @Test
    public void testSignIn_ValidCredentials() {
        // Create a sign-in request with valid email and password
        SignInRequest signInRequest = new SignInRequest();
        signInRequest.setEmail("valid@example.com");
        signInRequest.setPassword("valid_password");

        // Mock a user with the provided valid email and encoded password
        User user = new User();
        user.setEmail(signInRequest.getEmail());
        user.setPassword("encoded_valid_password"); // Assuming encoded password for comparison

        // Mock userRepository.findByEmail() to return a non-empty Optional containing the user
        when(userRepository.findByEmail(signInRequest.getEmail())).thenReturn(Optional.of(user));

        // Call the method under test
        SignInResponse result = authenticationService.signIn(signInRequest);

        // Debugging: Print the result
        System.out.println("Result: " + result);

        // Assert that the result is not null
        assertNotNull("SignInResponse should not be null", result);
    }





    @Test(expected = IllegalArgumentException.class)
    public void testSignIn_NullEmail() {
        SignInRequest signInRequest = new SignInRequest();
        signInRequest.setEmail(null);
        signInRequest.setPassword("valid_password");

        authenticationService.signIn(signInRequest);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSignIn_NullPassword() {
        SignInRequest signInRequest = new SignInRequest();
        signInRequest.setEmail("valid@example.com");
        signInRequest.setPassword(null);

        authenticationService.signIn(signInRequest);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testResetPassword_InvalidToken() {
        ResetPassRequest resetPassRequest = new ResetPassRequest();
        resetPassRequest.setToken("invalid_token");
        resetPassRequest.setPassword("new_password");

        // Mock passwordResetTokenRepository.findByToken() to return Optional.empty()
        when(passwordResetTokenRepository.findByToken(resetPassRequest.getToken())).thenReturn(Optional.empty());

        // Call the method under test
        authenticationService.resetPassword(resetPassRequest);
    }


    @Test
    public void testResetPassword_UserNotFoundForToken() {
        ResetPassRequest resetPassRequest = new ResetPassRequest();
        resetPassRequest.setToken("valid_token");
        resetPassRequest.setPassword("new_password");

        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setToken(resetPassRequest.getToken());

        User user = new User();
        user.setUserID(1); // Assuming user ID is set
        // Set other necessary user properties if required

        passwordResetToken.setUser(user);

        // Mock passwordResetTokenRepository.findByToken()
        when(passwordResetTokenRepository.findByToken(resetPassRequest.getToken())).thenReturn(Optional.of(passwordResetToken));

        // Mock userRepository.findById() returning the user
        when(userRepository.findById(any(Integer.class))).thenReturn(Optional.of(user));

        Object result = authenticationService.resetPassword(resetPassRequest);

        assertEquals("Password Changed Successfully", result);
    }

    @Test
    public void testForgotPassword() {
        String email = "user@example.com";
        ForgotPassRequest forgotPassRequest = new ForgotPassRequest();
        forgotPassRequest.setEmail(email);
        User mockUser = new User();

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(mockUser));
        when(passwordResetTokenRepository.findByUser(any(User.class))).thenReturn(Optional.empty()); // Simulate no existing token

        ForgotPassResponse response = authenticationService.forgotPassword(forgotPassRequest);

        assertNotNull(response);
        assertEquals("Email Sent Successfully", response.getMsg());
    }

    @Test
    public void testRefreshToken() {
        String oldToken = "oldRefreshToken";
        String newToken = "newAccessToken";
        String userEmail = "user@example.com";
        RefreshTokenRequest refreshTokenRequest = new RefreshTokenRequest();
        refreshTokenRequest.setToken(oldToken);

        User mockUser = new User();
        when(userRepository.findByEmail(userEmail)).thenReturn(Optional.of(mockUser));
        when(jwtService.extractUserName(refreshTokenRequest.getToken())).thenReturn(userEmail);
        when(jwtService.onRefreshToken(refreshTokenRequest, mockUser)).thenReturn(newToken);

        SignInResponse response = authenticationService.refreshToken(refreshTokenRequest);

        assertNotNull(response);
        assertEquals(newToken, response.getToken());
        assertEquals(oldToken, response.getRefreshToken());
    }
}
