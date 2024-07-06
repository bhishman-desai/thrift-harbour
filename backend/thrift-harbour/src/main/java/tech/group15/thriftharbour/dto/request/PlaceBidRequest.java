package tech.group15.thriftharbour.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlaceBidRequest {

  @NotNull @NotBlank String auctionSaleListingID;

  @NotNull @NotBlank Double bidAmount;
}
