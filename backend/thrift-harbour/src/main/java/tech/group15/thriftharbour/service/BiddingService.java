package tech.group15.thriftharbour.service;

import tech.group15.thriftharbour.dto.request.PlaceBidRequest;

import java.util.Optional;

public interface BiddingService {

    String placeBid(String authorizationHeader, PlaceBidRequest placeBidRequest);
}
