package tech.group15.thriftharbour.service;

import org.springframework.web.multipart.MultipartFile;
import tech.group15.thriftharbour.dto.request.SubmitListingRequest;
import tech.group15.thriftharbour.dto.response.*;
import tech.group15.thriftharbour.model.AuctionSaleListing;
import tech.group15.thriftharbour.model.ImmediateSaleListing;

import java.util.List;

public interface ProductListingService {

    // Creates an immediate sale listing for the submitted form and saves the respective images in S3 bucket
    ImmediateSaleListingCreationResponse createImmediateSaleListing(String authorizationHeader, SubmitListingRequest listingRequest, List<MultipartFile> productImages);

    // Creates an auction sale listing for the submitted form and saves the respective images in S3 bucket
    AuctionSaleListingCreationResponse createAuctionSaleListing(String authorizationHeader, SubmitListingRequest listingRequest, List<MultipartFile> productImages);

    ImmediateSaleListing findImmediateSaleListingByID(String immediateSaleListingID);

    /* Gets all product listing for admin */
    List<ImmediateSaleMinifiedResponse> findAllImmediateSaleListing();

    List<ImmediateSaleListing> findAllImmediateSaleListingBySellerEmail(String authorizationHeader);

    List<AuctionSaleListing> findAllAuctionSaleListingBySellerEmail(String authorizationHeader);

    GetListingImageResponse findAllImmediateSaleListingImagesByID(String ListingID);

    List<ImmediateSaleListing> findUserListingById(Integer sellerID);

    GetListingImageResponse findAllAuctionSaleListingImagesByID(String ListingID);

    List<ApprovedImmediateSaleListingForAdminResponse>findAllApprovedImmediateSaleListing();

    List<DeniedImmediateSaleListingForAdminResponse>findAllDeniedImmediateSaleListing();

    List<ApprovedAuctionSaleListingForAdminResponse>findAllApprovedAuctionSaleListing();

    List<DeniedAuctionSaleListingForAdminResponse>findAllDeniedAuctionSaleListing();

    AuctionSaleProductResponse findAuctionSaleProductDetailsById(String auctionSaleListingID);
}
