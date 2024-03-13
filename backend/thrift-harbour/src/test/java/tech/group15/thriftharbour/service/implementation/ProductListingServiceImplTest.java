package tech.group15.thriftharbour.service.implementation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tech.group15.thriftharbour.dto.*;
import tech.group15.thriftharbour.enums.RoleEnum;
import tech.group15.thriftharbour.model.*;
import tech.group15.thriftharbour.repository.*;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
class ProductListingServiceImplTest {
    @Mock
    AuctionSaleListingRepository auctionSaleListingRepository;
    @Mock
    AuctionSaleImageRepository auctionSaleImageRepository;
    @Mock
    UserRepository userRepository;
    @InjectMocks
    ProductListingServiceImpl productListingServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAuctionSaleProductDetailsById(){
        when(auctionSaleListingRepository.findAuctionSaleProductByID(anyString())).thenReturn(new AuctionSaleListing("auctionSaleListingID", "productName", "productDescription", 0d, 0d, "currentHighestBidUserMail", "category", "sellerEmail", new GregorianCalendar(2024, Calendar.MARCH, 13, 12, 44).getTime(), true, true, true, "approverEmail", "messageFromApprover", true, new GregorianCalendar(2024, Calendar.MARCH, 13, 12, 44).getTime(), new GregorianCalendar(2024, Calendar.MARCH, 13, 12, 44).getTime(), new GregorianCalendar(2024, Calendar.MARCH, 13, 12, 44).getTime()));
        when(auctionSaleImageRepository.findAllByAuctionSaleListingID(anyString())).thenReturn(List.of(new AuctionSaleImage(0, "auctionSaleListingID", "imageURL", new GregorianCalendar(2024, Calendar.MARCH, 13, 12, 44).getTime())));
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(new User(1, "firstName", "lastName", "email", "password", RoleEnum.USER, 4.0, 5.0)));

        AuctionSaleProductResponse result = productListingServiceImpl.findAuctionSaleProductDetailsById("auctionSaleListingID");
        Assertions.assertNotNull(result);

        verify(auctionSaleListingRepository, times(1)).findAuctionSaleProductByID("auctionSaleListingID");
        verify(auctionSaleImageRepository, times(1)).findAllByAuctionSaleListingID("auctionSaleListingID");
        verify(userRepository, times(1)).findByEmail(anyString());
    }
}
