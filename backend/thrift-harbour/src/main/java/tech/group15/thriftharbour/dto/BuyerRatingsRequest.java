package tech.group15.thriftharbour.dto;

import lombok.Data;

@Data
public class BuyerRatingsRequest {

  private int ratingToUserId;

  private int buyerRatings;
}
