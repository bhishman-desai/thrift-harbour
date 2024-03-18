package tech.group15.thriftharbour.service.implementation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tech.group15.thriftharbour.constant.InfoConstant;
import tech.group15.thriftharbour.enums.RoleEnum;
import tech.group15.thriftharbour.model.AuctionSaleListing;
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
    @Mock
    AuctionSaleListingRepository auctionSaleListingRepository;
    @Mock
    BiddingRepository biddingRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    JWTService jwtService;

    @InjectMocks
    BiddingServiceImpl biddingServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPlaceBid(){

        when(auctionSaleListingRepository.findByAuctionSaleListingID(anyString())).thenReturn(new AuctionSaleListing("auctionSaleListingID", "productName", "productDescription", 0d, 0d, "currentHighestBidUserMail", "category", "sellerEmail", new GregorianCalendar(2024, Calendar.MARCH, 13, 12, 44).getTime(), true, true, true, "approverEmail", "messageFromApprover", false, new GregorianCalendar(2024, Calendar.MARCH, 13, 12, 44).getTime(), new GregorianCalendar(2024, Calendar.MARCH, 13, 12, 44).getTime(), new GregorianCalendar(2024, Calendar.MARCH, 13, 12, 44).getTime()));
        when(jwtService.extractUserNameFromRequestHeaders(anyString())).thenReturn("sellerEmail");
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(new User(1, "firstName", "lastName", "email", "password", RoleEnum.USER, 4.0, 5.0)));

        String result = biddingServiceImpl.placeBid("authorizationHeader", "rand_uuid", 1.0);

        Assertions.assertEquals(InfoConstant.BID_PLACED_SUCCESFULLY, result);
    }
}
