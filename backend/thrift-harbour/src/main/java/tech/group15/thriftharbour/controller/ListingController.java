package tech.group15.thriftharbour.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tech.group15.thriftharbour.dto.response.AuctionSaleListingCreationResponse;
import tech.group15.thriftharbour.dto.response.GetListingImageResponse;
import tech.group15.thriftharbour.dto.response.ImmediateSaleListingCreationResponse;
import tech.group15.thriftharbour.dto.request.SubmitListingRequest;
import tech.group15.thriftharbour.model.AuctionSaleListing;
import tech.group15.thriftharbour.model.ImmediateSaleListing;
import tech.group15.thriftharbour.service.ProductListingService;

@RestController
@RequestMapping("/api/v1/users/listing")
@RequiredArgsConstructor
@Tag(name = "Listing")
public class ListingController {

  private final ProductListingService productListingService;

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

  // Get all immediate sale listing of the user
  @GetMapping("/get-immediatesale-listing")
  public ResponseEntity<List<ImmediateSaleListing>> getImmediateSaleListings(
      @Valid @RequestHeader("Authorization") String authorizationHeader) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(productListingService.findAllImmediateSaleListingBySellerEmail(authorizationHeader));
  }

  // get image url associated with the immediatesale listing
  @GetMapping("/get-immediatesale-images/{id}")
  public ResponseEntity<GetListingImageResponse> getImmediateSaleListingsImages(
      @PathVariable String id) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(productListingService.findAllImmediateSaleListingImagesByID(id));
  }

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

  @GetMapping("/get-auctionsale-listing")
  public ResponseEntity<List<AuctionSaleListing>> getAuctionSaleListings(
      @Valid @RequestHeader("Authorization") String authorizationHeader) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(productListingService.findAllAuctionSaleListingBySellerEmail(authorizationHeader));
  }

  @GetMapping("/get-auctionsale-images/{id}")
  public ResponseEntity<GetListingImageResponse> getAuctionSaleListingsImages(
      @PathVariable String id) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(productListingService.findAllAuctionSaleListingImagesByID(id));
  }

  /**
   * Retrieves the details of an auction sale product by its ID.
   *
   * @param id The unique id of the auction sale product.
   * @return A {@code ResponseEntity} containing the {@code AuctionSaleProductResponse} object.
   */
  @GetMapping("/get-auctionsale-product/{id}")
  public ResponseEntity<AuctionSaleProductResponse> getAuctionSaleProduct(
          @PathVariable String id) {
    return ResponseEntity.status(HttpStatus.OK)
            .body(productListingService.findAuctionSaleProductDetailsById(id));
  }
}
