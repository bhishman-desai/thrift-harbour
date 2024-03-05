package tech.group15.thriftharbour.dto;

import lombok.Data;

@Data
public class SellerRatingsRequest {

  private int ratingToUserId;

  private int sellerRatings;
}
