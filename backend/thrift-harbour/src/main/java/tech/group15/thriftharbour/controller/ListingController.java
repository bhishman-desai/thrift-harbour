package tech.group15.thriftharbour.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.group15.thriftharbour.dto.AuctionSaleListingCreationResponse;
import tech.group15.thriftharbour.dto.GetListingImageResponse;
import tech.group15.thriftharbour.dto.ImmediateSaleListingCreationResponse;
import tech.group15.thriftharbour.dto.SubmitListingRequest;
import tech.group15.thriftharbour.model.AuctionSaleListing;
import tech.group15.thriftharbour.model.ImmediateSaleListing;
import tech.group15.thriftharbour.service.ProductListingService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/listing")
@RequiredArgsConstructor
public class ListingController {

    @Autowired
    ProductListingService productListingService;

    @PostMapping("/create-immediatesale-listing")
    public ResponseEntity<ImmediateSaleListingCreationResponse> createImmediateListing
            (@Valid @RequestHeader("Authorization") String authorizationHeader,
             @ModelAttribute SubmitListingRequest submitListingRequest) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productListingService.CreateImmediateSaleListing(authorizationHeader, submitListingRequest));
    }

    // Get all immediate sale listing of the user
    @GetMapping("/get-immediatesale-listing")
    public ResponseEntity<List<ImmediateSaleListing>> getImmediateSaleListings
            (@Valid @RequestHeader("Authorization") String authorizationHeader) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(productListingService.findAllImmediateSaleListingBySellerEmail(authorizationHeader));
    }

    // get image url associated with the immediatesale listing
    @GetMapping("/get-immediatesale-images/{id}")
    public ResponseEntity<GetListingImageResponse> getImmediateSaleListingsImages(@PathVariable String id){
        return ResponseEntity.status(HttpStatus.OK)
                .body(productListingService.findAllImmediateSaleListingImagesByID(id));
    }

    @PostMapping("/create-auctionsale-listing")
    public ResponseEntity<AuctionSaleListingCreationResponse> createAuctionListing
            (@Valid @RequestHeader("Authorization") String authorizationHeader,
             @ModelAttribute SubmitListingRequest submitListingRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productListingService.CreateAuctionSaleListing(authorizationHeader, submitListingRequest));
    }

    @GetMapping("/get-auctionsale-listing")
    public ResponseEntity<List<AuctionSaleListing>> getAuctionSaleListings
            (@Valid @RequestHeader("Authorization") String authorizationHeader) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(productListingService.findAllAuctionSaleListingBySellerEmail(authorizationHeader));
    }

    @GetMapping("/get-auctionsale-images/{id}")
    public ResponseEntity<GetListingImageResponse> getAuctionSaleListingsImages(@PathVariable String id){
        return ResponseEntity.status(HttpStatus.OK)
                .body(productListingService.findAllAuctionSaleListingImagesByID(id));
    }
}
