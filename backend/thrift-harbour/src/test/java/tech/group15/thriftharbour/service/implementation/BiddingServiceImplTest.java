package tech.group15.thriftharbour.service.implementation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import tech.group15.thriftharbour.constant.InfoConstant;
import tech.group15.thriftharbour.enums.RoleEnum;
import tech.group15.thriftharbour.exception.ListingNotFoundException;
import tech.group15.thriftharbour.exception.LowBidException;
import tech.group15.thriftharbour.model.AuctionSaleListing;
import tech.group15.thriftharbour.model.Bidding;
import tech.group15.thriftharbour.model.User;
import tech.group15.thriftharbour.repository.AuctionSaleListingRepository;
import tech.group15.thriftharbour.repository.BiddingRepository;
import tech.group15.thriftharbour.repository.UserRepository;
import tech.group15.thriftharbour.service.JWTService;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Optional;

import static org.mockito.Mockito.*;

class BiddingServiceImplTest {
  @Mock AuctionSaleListingRepository auctionSaleListingRepository;
  @Mock BiddingRepository biddingRepository;

  @Mock UserRepository userRepository;

  @Mock JWTService jwtService;

  @InjectMocks BiddingServiceImpl biddingServiceImpl;

  AuctionSaleListing auctionProduct;

  Bidding userBid;

  User user;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    auctionProduct =
        new AuctionSaleListing(
            "auctionSaleListingID",
            "productName",
            "productDescription",
            100d,
            100d,
            "currentHighestBidUserMail",
            "category",
            "sellerEmail",
            new GregorianCalendar(2024, Calendar.MARCH, 13, 12, 44).getTime(),
            true,
            true,
            true,
            "approverEmail",
            "messageFromApprover",
            false,
            new GregorianCalendar(2024, Calendar.MARCH, 13, 12, 44).getTime(),
            new GregorianCalendar(2024, Calendar.MARCH, 13, 12, 44).getTime(),
            new GregorianCalendar(2024, Calendar.MARCH, 13, 12, 44).getTime());
    userBid = new Bidding(1, "rand_uuid", "test@testmail.com", 120.0);
    user = new User(1, "firstName", "lastName", "email", "password", RoleEnum.USER, 4.0, 5.0);
  }

  @Test
  void testPlaceBid() {

    when(auctionSaleListingRepository.findByAuctionSaleListingID(anyString()))
        .thenReturn(auctionProduct);
    when(auctionSaleListingRepository.save(any())).thenReturn(auctionProduct);
    when(jwtService.extractUserNameFromRequestHeaders(anyString())).thenReturn("sellerEmail");
    when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
    when(biddingRepository.save(any())).thenReturn(userBid);

    String result = biddingServiceImpl.placeBid("authorizationHeader", "rand_uuid", 120.0);

    Assertions.assertEquals(InfoConstant.BID_PLACED_SUCCESFULLY, result);
  }

  @Test
  void testPlaceBidWhenUserNotFound() {

    when(auctionSaleListingRepository.findByAuctionSaleListingID(anyString()))
        .thenReturn(auctionProduct);
    when(auctionSaleListingRepository.save(any())).thenReturn(auctionProduct);
    when(jwtService.extractUserNameFromRequestHeaders(anyString())).thenReturn("sellerEmail");
    when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
    when(biddingRepository.save(any())).thenReturn(userBid);

    Assertions.assertThrows(
        UsernameNotFoundException.class,
        () -> biddingServiceImpl.placeBid("authorizationHeader", "rand_uuid", 1.0));
  }

  @Test
  void testPlaceBidWhenProductNotFound() {
    when(auctionSaleListingRepository.findByAuctionSaleListingID(anyString())).thenReturn(null);
    when(auctionSaleListingRepository.save(any())).thenReturn(auctionProduct);
    when(jwtService.extractUserNameFromRequestHeaders(anyString())).thenReturn("sellerEmail");
    when(userRepository.findByEmail(anyString())).thenReturn(Optional.ofNullable(user));
    when(biddingRepository.save(any())).thenReturn(userBid);

    Assertions.assertThrows(
        ListingNotFoundException.class,
        () -> biddingServiceImpl.placeBid("authorizationHeader", "rand_uuid", 1.0));
  }

  @Test
  void testPlaceBidWhenUserBidLessThanCurrentBid() {
    when(auctionSaleListingRepository.findByAuctionSaleListingID(anyString()))
        .thenReturn(auctionProduct);
    when(auctionSaleListingRepository.save(any())).thenReturn(auctionProduct);
    when(jwtService.extractUserNameFromRequestHeaders(anyString())).thenReturn("sellerEmail");
    when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
    when(biddingRepository.save(any())).thenReturn(userBid);

    Assertions.assertThrows(
        LowBidException.class,
        () -> biddingServiceImpl.placeBid("authorizationHeader", "rand_uuid", 100.0));
  }
}
