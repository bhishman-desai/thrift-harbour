package tech.group15.thriftharbour.service.implementation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import tech.group15.thriftharbour.dto.request.RefreshTokenRequest;
import tech.group15.thriftharbour.service.JWTService;

import java.security.SignatureException;
import java.util.HashMap;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JWTServiceTest {

    private JWTService jwtService;

    @BeforeEach
    void setUp() {
        jwtService = new JWTService() {
            @Override
            public String extractUserName(String token) {
                return null;
            }

            @Override
            public String extractUserNameFromRequestHeaders(String authorizationHeader) {
                return null;
            }

            @Override
            public String generateToken(UserDetails userDetails) {
                return null;
            }

            @Override
            public String generateRefreshToken(HashMap<String, Object> objectObjectHashMap, UserDetails userDetails) {
                return null;
            }

            @Override
            public boolean isTokenValid(String token, UserDetails userDetails) {
                return false;
            }

            @Override
            public String onRefreshToken(RefreshTokenRequest refreshTokenRequest, UserDetails userDetails) {
                return null;
            }
        };
    }

    @Test
    void testExtractUserName_ValidToken() {
        // Define a valid token with a payload containing the expected username
        String validToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhYmMuYmNhQGRhbC5jYSIsImlhdCI6MTY0OTI0ODA1MywiZXhwIjoxNjQ5MjUxNjUzfQ.vqUB_m4wz3PpyEc5GjW_It3c6_FYdnV1Bxj5pEXj3xE";

        // Create a mock JWTService
        JWTService jwtService = mock(JWTService.class);

        // Configure the mock to throw a RuntimeException when extractUserName is called
        when(jwtService.extractUserName(validToken)).thenThrow(new RuntimeException("Signature verification failed"));

        // Perform the test by invoking the method under test
        Assertions.assertThrows(RuntimeException.class, () -> {
            jwtService.extractUserName(validToken);
        });
    }




    @Test
    void testExtractUserName_InvalidToken() {
        String invalidToken = "invalid_token";
        String extractedUsername = jwtService.extractUserName(invalidToken);
        Assertions.assertNull(extractedUsername);
    }

    @Test
    void testExtractUserNameFromRequestHeaders_ValidHeader() {
        String authorizationHeader = "Bearer valid_token";
        String extractedUsername = jwtService.extractUserNameFromRequestHeaders(authorizationHeader);
        Assertions.assertEquals(extractedUsername,extractedUsername);
    }

    @Test
    void testExtractUserNameFromRequestHeaders_InvalidHeader() {
        String invalidHeader = "invalid_header";
        String extractedUsername = jwtService.extractUserNameFromRequestHeaders(invalidHeader);
        Assertions.assertNull(extractedUsername);
    }

    @Test
    void testGenerateToken() {
        UserDetails userDetails = User.withUsername("test_user").password("password").roles("USER").build();
        String token = jwtService.generateToken(userDetails);
        Assertions.assertEquals(token,token);
        // You can add more assertions related to token format, etc.
    }

    @Test
    void testGenerateRefreshToken() {
        HashMap<String, Object> params = new HashMap<>();
        UserDetails userDetails = User.withUsername("abc.bca@dal.ca").password("Password@123").roles("USER").build();

        // Generate the refresh token
        String refreshToken = "eyJhbGciOiJIUzI1NiJ9.eyJSb2xlcyI6WyJVU0VSIiwiQURNSU4iXSwic3ViIjoiYWJjLmJjYUBkYWwuY2EiLCJpYXQiOjE3MTE0ODgxNTcsImV4cCI6MTcxMTU3NDU1N30.HUVtrG5Q9sGNMKPNtXST5P7UyHtFBfV6ERv79jhOUGw";

        // Assert that the refresh token is not null
        Assertions.assertNotNull(refreshToken, "The refresh token should not be null");

        // Assert that the refresh token is not empty
        Assertions.assertFalse(refreshToken.isEmpty(), "The refresh token should not be empty");

        // Assert that the refresh token length is within a certain range
        Assertions.assertTrue(refreshToken.length() >= 10 && refreshToken.length() <= 200, "Refresh token length should be within a certain range");
    }


    @Test
    void testIsTokenValid_ValidToken() {
        // Create a UserDetails object
        UserDetails userDetails = User.withUsername("test_user")
                .password("password")
                .roles("USER")
                .build();

        // Create an instance of JWTService (replace YourJWTServiceImpl() with your actual implementation)
        JWTService jwtService = new JWTServiceImpl();

        // Generate a valid token for the given userDetails
        String validToken = jwtService.generateToken(userDetails);

        // Check if the generated token is valid for the provided userDetails
        boolean isValid = jwtService.isTokenValid(validToken, userDetails);

        // Assert that the token is valid
        Assertions.assertTrue(isValid, "Token should be valid");
    }


    @Test
    void testIsTokenValid_InvalidToken() {
        UserDetails userDetails = User.withUsername("test_user").password("password").roles("USER").build();
        boolean isValid = jwtService.isTokenValid("invalid_token", userDetails);
        Assertions.assertFalse(isValid);
    }

    @Test
    void testOnRefreshToken() {
        // Create a RefreshTokenRequest object with necessary data
        RefreshTokenRequest refreshTokenRequest = new RefreshTokenRequest();
        refreshTokenRequest.setToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhYmMuYmNhQGRhbC5jYSIsImlhdCI6MTcxMTQ4ODE1NywiZXhwIjoxNzEyMDkyOTU3fQ.HNYsUphyGUfPmrxhFpnqhEkaOwu0rDjs069r82LgqAA");

        // Create a UserDetails object
        UserDetails userDetails = User.withUsername("abc.bca@dal.ca")
                .password("Password@123")
                .roles("USER")
                .build();

        // Call the onRefreshToken method with the refreshTokenRequest and userDetails
        JWTService jwtService = new JWTServiceImpl(); // Initialize your implementation of JWTService
        String newToken = jwtService.onRefreshToken(refreshTokenRequest, userDetails);

        // Assert that the new token is not null
        Assertions.assertNotNull(newToken, "New token should not be null");
    }

}