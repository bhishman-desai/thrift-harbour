package tech.group15.thriftharbour.service.implementation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

class BiddingServiceImplTest {
    BiddingServiceImpl biddingServiceImpl = new BiddingServiceImpl();

    @Test
    void testPlaceBid(){
        Optional<String> result = biddingServiceImpl.placeBid("authorizationHeader", Double.valueOf(0));
        Assertions.assertEquals(null, result);
    }
}
