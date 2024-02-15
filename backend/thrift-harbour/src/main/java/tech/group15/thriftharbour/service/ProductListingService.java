package tech.group15.thriftharbour.service;

import tech.group15.thriftharbour.dto.AuctionSaleListingCreationResponse;
import tech.group15.thriftharbour.dto.ImmediateSaleListingCreationResponse;
import tech.group15.thriftharbour.dto.SubmitListingRequest;

public interface ProductListingService {

    // Creates an immediate sale listing for the submitted form and saves the respective images in S3 bucket
    ImmediateSaleListingCreationResponse CreateImmediateSaleListing(String authorizationHeader, SubmitListingRequest listingRequest);

    // Creates an auction sale listing for the submitted form and saves the respective images in S3 bucket
    AuctionSaleListingCreationResponse CreateAuctionSaleListing(String authorizationHeader, SubmitListingRequest listingRequest);
}
