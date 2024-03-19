package tech.group15.thriftharbour.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.group15.thriftharbour.dto.request.PlaceBidRequest;
import tech.group15.thriftharbour.service.BiddingService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
@Tag(name = "Bidding")
public class BiddingController {

  private final BiddingService biddingService;

  /**
   * Retrieves all immediate sale listings with its seller details.
   *
   * @param authorizationHeader The authorization header containing the JWT of user.
   * @param placeBidRequest Containing the Listing ID and the bid to be placed.
   * @return Message to user - Bid is placed successfully.
   */
  @PostMapping("/place-bid")
  public ResponseEntity<String> placeBid(
          @Valid @RequestHeader("Authorization") String authorizationHeader,
          @RequestBody PlaceBidRequest placeBidRequest) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(biddingService.placeBid(authorizationHeader, placeBidRequest));
  }
}
