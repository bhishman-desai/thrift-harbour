package tech.group15.thriftharbour.service;

import tech.group15.thriftharbour.dto.*;
import tech.group15.thriftharbour.model.AuctionSaleListing;
import tech.group15.thriftharbour.model.ImmediateSaleListing;

import java.util.List;

public interface ProductListingService {

    // Creates an immediate sale listing for the submitted form and saves the respective images in S3 bucket
    ImmediateSaleListingCreationResponse createImmediateSaleListing(String authorizationHeader, SubmitListingRequest listingRequest);

    // Creates an auction sale listing for the submitted form and saves the respective images in S3 bucket
    AuctionSaleListingCreationResponse createAuctionSaleListing(String authorizationHeader, SubmitListingRequest listingRequest);

    /* Get a single product for view */
    ImmediateSaleListing findImmediateSaleListingByID(String immediateSaleListingID);

    /* Gets all product listing for admin */
    List<ImmediateSaleMinifiedResponse> findAllImmediateSaleListing();

    List<ImmediateSaleListing> findAllImmediateSaleListingBySellerEmail(String authorizationHeader);

    List<AuctionSaleListing> findAllAuctionSaleListingBySellerEmail(String authorizationHeader);

    GetListingImageResponse findAllImmediateSaleListingImagesByID(String listingID);

    GetListingImageResponse findAllAuctionSaleListingImagesByID(String listingID);

    List<ImmediateSaleListing> findUserListingById(Integer sellerID);
}
