package tech.group15.thriftharbour.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tech.group15.thriftharbour.dto.request.SubmitListingRequest;
import tech.group15.thriftharbour.dto.response.AuctionSaleListingCreationResponse;
import tech.group15.thriftharbour.dto.response.GetListingImageResponse;
import tech.group15.thriftharbour.dto.response.ImmediateSaleListingCreationResponse;
import tech.group15.thriftharbour.model.AuctionSaleListing;
import tech.group15.thriftharbour.model.ImmediateSaleListing;
import tech.group15.thriftharbour.service.ProductListingService;

@RestController
@RequestMapping("/api/v1/users/listing")
@RequiredArgsConstructor
@Tag(name = "Listing")
public class ListingController {

  private final ProductListingService productListingService;

  /**
   * Post request to create a immediate sale listing.
   *
   * @param authorizationHeader The authorization header containing the JWT of user.
   * @param submitListingRequest has the listing details
   * @param productImages has the byte data of uploaded images
   * @return A list of {@code ImmediateSaleListingCreationResponse}  containing the
   * details of listed product
   */
  @PostMapping("/create-immediatesale-listing")
  public ResponseEntity<ImmediateSaleListingCreationResponse> createImmediateListing(
      @Valid @RequestHeader("Authorization") String authorizationHeader,
      @ModelAttribute SubmitListingRequest submitListingRequest,
      @RequestParam("productImages") List<MultipartFile> productImages) {

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(
            productListingService.createImmediateSaleListing(
                authorizationHeader, submitListingRequest, productImages));
  }

  /**
   * Get all immediate sale listing of the user.
   *
   * @param authorizationHeader The authorization header containing the JWT of user.
   * @return A list of {@code ImmediateSaleListing}  containing the
   * details of products listed by the logged in user
   */
  @GetMapping("/get-immediatesale-listing")
  public ResponseEntity<List<ImmediateSaleListing>> getImmediateSaleListings(
      @Valid @RequestHeader("Authorization") String authorizationHeader) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(productListingService.findAllImmediateSaleListingBySellerEmail(authorizationHeader));
  }

  /**
   * get image url associated with the immediatesale listing.
   *
   * @param id listing id of a particular listing.
   * @return A list of {@code GetListingImageResponse}  containing the
   * image URL of images associated with provided listing id
   */
  @GetMapping("/get-immediatesale-images/{id}")
  public ResponseEntity<GetListingImageResponse> getImmediateSaleListingsImages(
      @PathVariable String id) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(productListingService.findAllImmediateSaleListingImagesByID(id));
  }

  /**
   * Post request to create a immediate sale listing.
   *
   * @param authorizationHeader The authorization header containing the JWT of user.
   * @param submitListingRequest has the listing details
   * @param productImages has the byte data of uploaded images
   * @return A list of {@code AuctionSaleListingCreationResponse}  containing the
   * details of listed product
   */
  @PostMapping("/create-auctionsale-listing")
  public ResponseEntity<AuctionSaleListingCreationResponse> createAuctionListing(
      @Valid @RequestHeader("Authorization") String authorizationHeader,
      @ModelAttribute SubmitListingRequest submitListingRequest,
      @RequestParam("productImages") List<MultipartFile> productImages) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(
            productListingService.createAuctionSaleListing(
                authorizationHeader, submitListingRequest, productImages));
  }

  /**
   * Get all auction sale listing of the user.
   *
   * @param authorizationHeader The authorization header containing the JWT of user.
   * @return A list of {@code ImmediateSaleListing}  containing the
   * details of products listed by the logged in user
   */
  @GetMapping("/get-auctionsale-listing")
  public ResponseEntity<List<AuctionSaleListing>> getAuctionSaleListings(
      @Valid @RequestHeader("Authorization") String authorizationHeader) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(productListingService.findAllAuctionSaleListingBySellerEmail(authorizationHeader));
  }

  /**
   * get image url associated with the auctionsale listing.
   *
   * @param id listing id of a particular listing.
   * @return A list of {@code GetListingImageResponse}  containing the
   * image URL of images associated with provided listing id
   */
  @GetMapping("/get-auctionsale-images/{id}")
  public ResponseEntity<GetListingImageResponse> getAuctionSaleListingsImages(
      @PathVariable String id) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(productListingService.findAllAuctionSaleListingImagesByID(id));
  }

  /**
   * GET request to retrieve all immediate sale listings for the authenticated user.
   *
   * @param authorizationHeader The authorization header containing the JWT of user.
   * @return A list of {@code ResponseEntity} objects containing list of {@code
   *     ImmediateSaleListingCreationResponse} representing all immediate sale listings details.
   */
  @GetMapping("/get-all-immediate-listing")
  public ResponseEntity<List<ImmediateSaleListingCreationResponse>> getAllImmediateListings(
      @Valid @RequestHeader("Authorization") String authorizationHeader) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(productListingService.findAllImmediateListing(authorizationHeader));
  }

  /**
   * GET request to retrieve all auction sale listings for the authenticated user.
   *
   * @param authorizationHeader The authorization header containing the JWT of user.
   * @return A list of {@code ResponseEntity} objects containing list of {@code
   *     AuctionSaleListingCreationResponse} representing all auction sale listings details.
   */
  @GetMapping("/get-all-auction-listing")
  public ResponseEntity<List<AuctionSaleListingCreationResponse>> getAllAuctionListings(
      @Valid @RequestHeader("Authorization") String authorizationHeader) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(productListingService.findAllAuctionListing(authorizationHeader));
  }
}
