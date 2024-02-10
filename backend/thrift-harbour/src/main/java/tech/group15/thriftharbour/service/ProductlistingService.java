package tech.group15.thriftharbour.service;

import tech.group15.thriftharbour.dto.SubmitListingRequest;

public interface ProductlistingService {

    void CreateImmediateSaleListing(String authToken, SubmitListingRequest listingRequest);

    void CreateAuctionSaleListing(String authToken, SubmitListingRequest listingRequest);
}
