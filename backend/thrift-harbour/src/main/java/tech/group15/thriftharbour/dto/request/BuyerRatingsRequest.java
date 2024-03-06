package tech.group15.thriftharbour.dto.request;

import lombok.Data;

@Data
public class BuyerRatingsRequest {

  private int ratingToUserId;

  private int buyerRatings;
}
