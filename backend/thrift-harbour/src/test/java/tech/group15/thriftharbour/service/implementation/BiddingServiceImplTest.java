package tech.group15.thriftharbour.service.implementation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tech.group15.thriftharbour.model.AuctionSaleListing;
import tech.group15.thriftharbour.repository.AuctionSaleListingRepository;
import tech.group15.thriftharbour.repository.BiddingRepository;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Optional;

import static org.mockito.Mockito.*;
class BiddingServiceImplTest {
    @Mock
    AuctionSaleListingRepository auctionSaleListingRepository;
    @Mock
    BiddingRepository biddingRepository;
    @InjectMocks
    BiddingServiceImpl biddingServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPlaceBid(){

        when(auctionSaleListingRepository.findAuctionSaleProductByID(anyString())).thenReturn(new AuctionSaleListing("auctionSaleListingID", "productName", "productDescription", 0d, 0d, "currentHighestBidUserMail", "category", "sellerEmail", new GregorianCalendar(2024, Calendar.MARCH, 13, 12, 44).getTime(), true, true, true, "approverEmail", "messageFromApprover", false, new GregorianCalendar(2024, Calendar.MARCH, 13, 12, 44).getTime(), new GregorianCalendar(2024, Calendar.MARCH, 13, 12, 44).getTime(), new GregorianCalendar(2024, Calendar.MARCH, 13, 12, 44).getTime()));
        when(auctionSaleListingRepository.save(any())).thenReturn("Listing saved succesfully");
        when(biddingRepository.save(any())).thenReturn("Bid placed succesfully");

        String result = biddingServiceImpl.placeBid("authorizationHeader", "rand_uuid",Double.valueOf(0));

        Assertions.assertEquals("", result);
    }
}
