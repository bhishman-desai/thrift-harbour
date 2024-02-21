package tech.group15.thriftharbour.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.group15.thriftharbour.dto.ListingReviewRequest;
import tech.group15.thriftharbour.dto.ListingReviewResponse;
import tech.group15.thriftharbour.dto.SignUpRequest;
import tech.group15.thriftharbour.model.User;
import tech.group15.thriftharbour.service.AdminService;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
@Tag(name = "Admin")
public class AdminController {

  private final AdminService adminService;

  @GetMapping
  public ResponseEntity<String> hi() {
    return ResponseEntity.ok("Hi from admin!");
  }


  @PostMapping("/review-request")
  public ResponseEntity<ListingReviewResponse> reviewRequest(
          @Valid @RequestHeader("Authorization") String authorizationHeader,
          @RequestBody ListingReviewRequest listingReviewRequest){
    return ResponseEntity.status(HttpStatus.OK)
            .body(adminService.reviewListing(authorizationHeader, listingReviewRequest));
  }
}
