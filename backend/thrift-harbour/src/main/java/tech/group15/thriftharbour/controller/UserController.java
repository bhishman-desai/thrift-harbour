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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.group15.thriftharbour.model.User;
import tech.group15.thriftharbour.service.UserService;

import java.util.List;


/** Controller class for handling user-related endpoints. */
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

  /* Get user by id */
  /**
   * Handles GET request to retrieve a user by ID.
   *
   * @param userID The ID of the user to be retrieved.
   * @return A {@code ResponseEntity} containing the retrieved {@code User} object.
   */
  @GetMapping("/users/{userID}")
  public ResponseEntity<User> getUserById(@PathVariable Integer userID) {
    return ResponseEntity.ok(userService.findUserById(userID));
  }

  /**
   * Retrieves the recipient(s) associated with the specified sender ID.
   *
   * @param senderId The unique identifier of the sender.
   * @return {@code ResponseEntity} containing a list of User objects representing the recipient(s).
   */
  @GetMapping("/{senderId}/recipients")
  public ResponseEntity<List<User>> getRecipientFromSenderId(@PathVariable String senderId) {
    return ResponseEntity.ok(userService.findRecipientBySenderId(senderId));
  }
}
