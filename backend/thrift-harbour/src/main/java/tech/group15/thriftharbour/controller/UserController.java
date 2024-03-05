package tech.group15.thriftharbour.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.group15.thriftharbour.dto.BuyerRatingsRequest;
import tech.group15.thriftharbour.dto.SellerRatingsRequest;
import tech.group15.thriftharbour.model.User;
import tech.group15.thriftharbour.service.RatingsService;
import tech.group15.thriftharbour.service.UserService;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Tag(name = "User")
public class UserController {
  private final RatingsService ratingsService;
  private final UserService userService;

  @GetMapping
  public ResponseEntity<String> hi() {
    return ResponseEntity.ok("Hi from user!");
  }

  @PostMapping("/add-buyer-ratings")
  public ResponseEntity<String> addBuyerRatings(
      @Valid @RequestHeader("Authorization") String authorizationHeader,
      @RequestBody BuyerRatingsRequest buyerRatingsRequest) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(ratingsService.addBuyerRatings(authorizationHeader, buyerRatingsRequest));
  }

  @PostMapping("/add-seller-ratings")
  public ResponseEntity<String> addSellerRatings(
      @Valid @RequestHeader("Authorization") String authorizationHeader,
      @RequestBody SellerRatingsRequest sellerRatingsRequest) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(ratingsService.addSellerRatings(authorizationHeader, sellerRatingsRequest));
  }

  @GetMapping("/get-user-details/{id}")
  public ResponseEntity<User> getUserDetails(@PathVariable Integer id) {
    return ResponseEntity.ok(userService.findUserById(id));
  }
}
