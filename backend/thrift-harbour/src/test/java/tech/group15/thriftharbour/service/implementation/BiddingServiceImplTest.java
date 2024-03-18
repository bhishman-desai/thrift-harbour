package tech.group15.thriftharbour.service.implementation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;import tech.group15.thriftharbour.repository.AuctionSaleListingRepository;import tech.group15.thriftharbour.repository.BiddingRepository;import java.util.Optional;
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
        Optional<String> result = biddingServiceImpl.placeBid("authorizationHeader", Double.valueOf(0));
        Assertions.assertEquals(null, result);
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme