package tech.group15.thriftharbour.service;

import tech.group15.thriftharbour.dto.BuyerRatingsRequest;

public interface RatingsService {
    String addBuyerRatings(String authorizationHeader, BuyerRatingsRequest buyerRatingsRequest);
}
