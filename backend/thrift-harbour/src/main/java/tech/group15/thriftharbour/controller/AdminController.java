package tech.group15.thriftharbour.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

  @Autowired
  ProductListingService productListingService;
  @GetMapping
  public ResponseEntity<String> hi() {
    return ResponseEntity.ok("Hi from admin!");
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
