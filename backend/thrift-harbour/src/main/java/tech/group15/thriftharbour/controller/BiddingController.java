package tech.group15.thriftharbour.controller;

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
public class BiddingController {

  private final BiddingService biddingService;

  @PostMapping("/place-bid")
  public ResponseEntity<String> placeBid(
          @Valid @RequestHeader("Authorization") String authorizationHeader,
          @RequestBody PlaceBidRequest placeBidRequest) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(biddingService.placeBid(authorizationHeader, placeBidRequest));
  }
}
