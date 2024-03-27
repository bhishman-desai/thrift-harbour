package tech.group15.thriftharbour.service;

import tech.group15.thriftharbour.dto.request.BuyerRatingsRequest;
import tech.group15.thriftharbour.dto.request.SellerRatingsRequest;

public interface RatingsService {
    String addBuyerRatings(String authorizationHeader, BuyerRatingsRequest buyerRatingsRequest);
    String addSellerRatings(String authorizationHeader, SellerRatingsRequest sellerRatingsRequest);
}
