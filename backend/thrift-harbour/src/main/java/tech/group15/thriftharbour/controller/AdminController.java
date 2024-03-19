package tech.group15.thriftharbour.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PathVariable;
import tech.group15.thriftharbour.dto.ListingReviewRequest;
import tech.group15.thriftharbour.dto.ListingReviewResponse;
import tech.group15.thriftharbour.service.AdminService;
import tech.group15.thriftharbour.dto.ImmediateSaleMinifiedResponse;
import tech.group15.thriftharbour.dto.SellerResponse;
import tech.group15.thriftharbour.model.ImmediateSaleListing;
import tech.group15.thriftharbour.model.User;
import tech.group15.thriftharbour.service.ProductListingService;
import tech.group15.thriftharbour.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.group15.thriftharbour.dto.ApprovedAuctionSaleListingForAdminResponse;
import tech.group15.thriftharbour.dto.ApprovedImmediateSaleListingForAdminResponse;
import tech.group15.thriftharbour.dto.DeniedAuctionSaleListingForAdminResponse;
import tech.group15.thriftharbour.dto.DeniedImmediateSaleListingForAdminResponse;
import tech.group15.thriftharbour.service.ProductListingService;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
@Tag(name = "Admin")
public class AdminController {
  private final ProductListingService productListingService;
  private final UserService userService;

  private final AdminService adminService;

  @GetMapping
  public ResponseEntity<String> hi() {
    return ResponseEntity.ok("Hi from admin!");
  }

  /* Get all immediate sale listing for admin*/
  @GetMapping("/get-all-immediatesale-listing")
  public ResponseEntity<List<ImmediateSaleMinifiedResponse>> getAllImmediateSaleListings() {
    return ResponseEntity.status(HttpStatus.OK)
        .body(productListingService.findAllImmediateSaleListing());
  }

  /* Get all seller who've listed their products */
  @GetMapping("/get-all-sellers")
  public ResponseEntity<List<SellerResponse>> getAllSellers() {
    return ResponseEntity.status(HttpStatus.OK)
            .body(userService.findAllSellers());
  }

  /* Get user by id */
  @GetMapping("/sellers/{sellerID}")
  public ResponseEntity<User> getUserById(@PathVariable Integer sellerID) {
    return ResponseEntity.ok(userService.findUserById(sellerID));
  }

  /* Get user listing by userid */
  @GetMapping("/sellers/{sellerID}/product-listing")
  public ResponseEntity<List<ImmediateSaleListing>> getUserListingByUserId(@PathVariable Integer sellerID) {
    return ResponseEntity.ok(productListingService.findUserListingById(sellerID));
  }

  /* Get single immediate sale product */
  @GetMapping("/get-immediatesale-product/{id}")
  public ResponseEntity<ImmediateSaleListing> getImmediateSaleProduct
  (@PathVariable String id) {
    return ResponseEntity.status(HttpStatus.OK)
            .body(productListingService.findImmediateSaleListingByID(id));
  }

  @Tag(name = "Approve/Reject User Listing")
  @PostMapping("/review-request")
  public ResponseEntity<ListingReviewResponse> reviewRequest(
      @Valid @RequestHeader("Authorization") String authorizationHeader,
      @RequestBody ListingReviewRequest listingReviewRequest) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(adminService.reviewListing(authorizationHeader, listingReviewRequest));
  }

  @GetMapping("/get-approved-immediatesale-listing")
  public ResponseEntity<List<ApprovedImmediateSaleListingForAdminResponse>> getApprovedImmediateSaleListings
          () {
    return ResponseEntity.status(HttpStatus.OK)
            .body(productListingService.findAllApprovedImmediateSaleListing());
  }

  @GetMapping("/get-denied-immediatesale-listing")
  public ResponseEntity<List<DeniedImmediateSaleListingForAdminResponse>> getDeniedImmediateSaleListings
          () {
    return ResponseEntity.status(HttpStatus.OK)
            .body(productListingService.findAllDeniedImmediateSaleListing());
  }

  @GetMapping("/get-approved-auctionsale-listing")
  public ResponseEntity<List<ApprovedAuctionSaleListingForAdminResponse>> getApprovedAuctionSaleListings
          () {
    return ResponseEntity.status(HttpStatus.OK)
            .body(productListingService.findAllApprovedAuctionSaleListing());
  }

  @GetMapping("/get-denied-auctionsale-listing")
  public ResponseEntity<List<DeniedAuctionSaleListingForAdminResponse>> getDeniedAuctionSaleListings
          () {
    return ResponseEntity.status(HttpStatus.OK)
            .body(productListingService.findAllDeniedAuctionSaleListing());
  }

}
