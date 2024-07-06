package tech.group15.thriftharbour.dto.request;

import lombok.Data;

@Data
public class SellerRatingsRequest {

  private int ratingToUserId;

  private int sellerRatings;
}
