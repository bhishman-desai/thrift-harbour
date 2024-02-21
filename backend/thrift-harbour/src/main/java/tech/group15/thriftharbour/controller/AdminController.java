package tech.group15.thriftharbour.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.group15.thriftharbour.dto.ImmediateSaleMinifiedResponse;
import tech.group15.thriftharbour.dto.SellerResponse;
import tech.group15.thriftharbour.service.ProductListingService;
import tech.group15.thriftharbour.service.UserService;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
@Tag(name = "Admin")
public class AdminController {
  private final ProductListingService productListingService;
  private final UserService userService;

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
}
