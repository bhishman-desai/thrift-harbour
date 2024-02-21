package tech.group15.thriftharbour.service;

import tech.group15.thriftharbour.dto.*;
import tech.group15.thriftharbour.model.AuctionSaleListing;
import tech.group15.thriftharbour.model.ImmediateSaleListing;

import java.util.List;

public interface ProductListingService {

    // Creates an immediate sale listing for the submitted form and saves the respective images in S3 bucket
    ImmediateSaleListingCreationResponse CreateImmediateSaleListing(String authorizationHeader, SubmitListingRequest listingRequest);

    // Creates an auction sale listing for the submitted form and saves the respective images in S3 bucket
    AuctionSaleListingCreationResponse CreateAuctionSaleListing(String authorizationHeader, SubmitListingRequest listingRequest);

    List<ImmediateSaleListing> findAllImmediateSaleListingBySellerEmail(String authorizationHeader);

    List<AuctionSaleListing> findAllAuctionSaleListingBySellerEmail(String authorizationHeader);

    GetListingImageResponse findAllImmediateSaleListingImagesByID(String ListingID);

    GetListingImageResponse findAllAuctionSaleListingImagesByID(String ListingID);

    List<ApprovedImmediateSaleListingForAdminResponse>findAllApprovedImmediateSaleListing();
    List<DeniedImmediateSaleListingForAdminResponse>findAllDeniedImmediateSaleListing();
}
