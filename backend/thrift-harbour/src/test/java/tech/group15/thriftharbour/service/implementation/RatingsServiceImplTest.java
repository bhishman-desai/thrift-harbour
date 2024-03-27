package tech.group15.thriftharbour.service.implementation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import tech.group15.thriftharbour.dto.request.BuyerRatingsRequest;
import tech.group15.thriftharbour.dto.request.SellerRatingsRequest;
import tech.group15.thriftharbour.enums.RoleEnum;
import tech.group15.thriftharbour.model.BuyerRatings;
import tech.group15.thriftharbour.model.SellerRatings;
import tech.group15.thriftharbour.model.User;
import tech.group15.thriftharbour.repository.BuyerRatingsRepository;
import tech.group15.thriftharbour.repository.SellerRatingsRepository;
import tech.group15.thriftharbour.repository.UserRepository;
import tech.group15.thriftharbour.service.JWTService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
class RatingsServiceImplTest {
    @Mock
    JWTService jwtService;
    @Mock
    UserRepository userRepository;
    @Mock
    BuyerRatingsRepository buyerRatingsRepository;
    @Mock
    SellerRatingsRepository sellerRatingsRepository;
    @InjectMocks
    RatingsServiceImpl ratingsServiceImpl;
    User user;
    String userEmail;
    String authorizationHeader;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userEmail = "user@gmail.com";
        authorizationHeader = "authorizationHeader";
        user = new User(1, "firstName", "lastName", userEmail, "password", RoleEnum.USER, 4.0, 5.0);
    }

    /**
     * Tests the add buyer ratings from authorized user.
     */
    @Test
    void testAddBuyerRatings(){
        when(jwtService.extractUserNameFromRequestHeaders(anyString())).thenReturn(userEmail);
        when(userRepository.findByEmail(userEmail)).thenReturn(Optional.of(user));
        when(buyerRatingsRepository.findAvgBuyerRatings(user.getUserID())).thenReturn(4.5);

        String result = ratingsServiceImpl.addBuyerRatings(authorizationHeader, new BuyerRatingsRequest());

        Assertions.assertEquals("Buyer Ratings Added Successfully", result);
        verify(userRepository).findByEmail(userEmail);
        verify(buyerRatingsRepository).save(any(BuyerRatings.class));
        verify(userRepository).updateBuyerRatings(anyDouble(), anyInt());
    }

    /**
     * Tests user not found while adding buyer ratings.
     */
    @Test
    void testAddBuyerRatingsUserNotFound() {
        when(jwtService.extractUserNameFromRequestHeaders(authorizationHeader)).thenReturn(userEmail);
        when(userRepository.findByEmail(userEmail)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> ratingsServiceImpl.addBuyerRatings(authorizationHeader, new BuyerRatingsRequest()));
        verify(userRepository).findByEmail(userEmail);
    }

    /**
     * Tests the add seller ratings from authorized user.
     */
    @Test
    void testAddSellerRatings(){
        when(jwtService.extractUserNameFromRequestHeaders(anyString())).thenReturn(userEmail);
        when(userRepository.findByEmail(userEmail)).thenReturn(Optional.of(user));
        when(sellerRatingsRepository.findAvgSellerRatings(user.getUserID())).thenReturn(4.2);

        String result = ratingsServiceImpl.addSellerRatings(authorizationHeader, new SellerRatingsRequest());

        Assertions.assertEquals("Seller Ratings Added Successfully", result);
        verify(userRepository).findByEmail(userEmail);
        verify(sellerRatingsRepository).save(any(SellerRatings.class));
        verify(userRepository).updateSellerRatings(anyDouble(), anyInt());
    }

    /**
     * Tests user not found while adding seller ratings.
     */
    @Test
    void testAddSellerRatingsUserNotFound() {
        when(jwtService.extractUserNameFromRequestHeaders(authorizationHeader)).thenReturn(userEmail);
        when(userRepository.findByEmail(userEmail)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> ratingsServiceImpl.addSellerRatings(authorizationHeader, new SellerRatingsRequest()));
        verify(userRepository).findByEmail(userEmail);
    }
}
