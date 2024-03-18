package tech.group15.thriftharbour.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.group15.thriftharbour.repository.AuctionSaleListingRepository;
import tech.group15.thriftharbour.repository.BiddingRepository;
import tech.group15.thriftharbour.service.BiddingService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BiddingServiceImpl implements BiddingService {

    private final AuctionSaleListingRepository auctionSaleListingRepository;

    private final BiddingRepository biddingRepository;

    @Override
    public String placeBid(String authorizationHeader,String auctionSaleListingID, Double bidAmount) {

        return "";
    }
}
