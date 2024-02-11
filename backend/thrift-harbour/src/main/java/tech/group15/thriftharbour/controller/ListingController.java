package tech.group15.thriftharbour.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.group15.thriftharbour.dto.SubmitListingRequest;
import tech.group15.thriftharbour.service.ProductlistingService;

@RestController
@RequestMapping("/api/v1/listing")
@RequiredArgsConstructor
public class ListingController {

    @Autowired
    ProductlistingService productlistingService;

    @PostMapping("create-immediatesale-listing")
    public ResponseEntity<String> createImmediateListing(@RequestHeader("Authorization") String authorizationHeader,
                                                         @ModelAttribute SubmitListingRequest submitListingRequest){
        productlistingService.CreateImmediateSaleListing(authorizationHeader, submitListingRequest);
        return ResponseEntity.ok("Listing created");
    }
}
