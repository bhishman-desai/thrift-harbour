package tech.group15.thriftharbour.service.implementation;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import tech.group15.thriftharbour.dto.*;
import tech.group15.thriftharbour.model.User;
import tech.group15.thriftharbour.repository.PasswordResetTokenRepository;
import tech.group15.thriftharbour.repository.UserRepository;
import tech.group15.thriftharbour.service.EmailService;
import tech.group15.thriftharbour.service.JWTService;

import static com.sun.tools.sjavac.options.Option.S;
import static org.mockito.Mockito.*;

public class AuthenticationServiceImplTest {
    @Mock
    UserRepository userRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    AuthenticationManager authenticationManager;
    @Mock
    JWTService jwtService;
    @Mock
    PasswordResetTokenRepository passwordResetTokenRepository;
    @Mock
    EmailService emailService;
    @InjectMocks
    AuthenticationServiceImpl authenticationServiceImpl;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSignUp() throws Exception {
        when(userRepository.existsByEmail(anyString())).thenReturn(true);
        when(userRepository.save(any())).thenReturn(new S());
        when(passwordResetTokenRepository.save(any())).thenReturn(new S());

        User result = authenticationServiceImpl.signUp(new SignUpRequest());
        Assert.assertEquals(new User(), result);
    }

    @Test
    public void testSignIn() throws Exception {
        when(userRepository.findByEmail(anyString())).thenReturn(null);
        when(authenticationManager.authenticate(any())).thenReturn(null);
        when(jwtService.generateToken(any())).thenReturn("generateTokenResponse");
        when(jwtService.generateRefreshToken(any(), any())).thenReturn("generateRefreshTokenResponse");

        SignInResponse result = authenticationServiceImpl.signIn(new SignInRequest());
        Assert.assertEquals(new SignInResponse(), result);
    }

    @Test
    public void testRefreshToken() throws Exception {
        when(userRepository.findByEmail(anyString())).thenReturn(null);
        when(jwtService.extractUserName(anyString())).thenReturn("extractUserNameResponse");
        when(jwtService.generateToken(any())).thenReturn("generateTokenResponse");
        when(jwtService.isTokenValid(anyString(), any())).thenReturn(true);

        SignInResponse result = authenticationServiceImpl.refreshToken(new RefreshTokenRequest());
        Assert.assertEquals(new SignInResponse(), result);
    }

    @Test
    public void testForgotPassword() throws Exception {
        when(userRepository.findByEmail(anyString())).thenReturn(null);
        when(userRepository.save(any())).thenReturn(new S());
        when(passwordResetTokenRepository.findByUser(any())).thenReturn(null);
        when(passwordResetTokenRepository.save(any())).thenReturn(new S());

        ForgotPassResponse result = authenticationServiceImpl.forgotPassword(new ForgotPassRequest());
        Assert.assertEquals(new ForgotPassResponse(), result);
    }

    @Test
    public void testResetPassTokenVerify() throws Exception {
        when(passwordResetTokenRepository.findByToken(anyString())).thenReturn(null);

        Object result = authenticationServiceImpl.resetPassTokenVerify("token");
        Assert.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    public void testResetPassword() throws Exception {
        when(userRepository.save(any())).thenReturn(new S());
        when(userRepository.findById(any())).thenReturn(null);
        when(passwordEncoder.encode(any())).thenReturn("encodeResponse");
        when(passwordResetTokenRepository.findByToken(anyString())).thenReturn(null);
        when(passwordResetTokenRepository.save(any())).thenReturn(new S());
        when(passwordResetTokenRepository.findById(any())).thenReturn(null);

        Object result = authenticationServiceImpl.resetPassword(new ResetPassRequest());
        Assert.assertEquals("replaceMeWithExpectedResult", result);
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme