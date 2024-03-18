package tech.group15.thriftharbour.service;

import java.util.Optional;

public interface BiddingService {

    String placeBid(String authorizationHeader, String auctionSaleListingID, Double bidAmount);
}
