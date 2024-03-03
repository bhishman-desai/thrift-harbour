package tech.group15.thriftharbour.mapper;

import tech.group15.thriftharbour.dto.BuyerRatingsRequest;
import tech.group15.thriftharbour.model.BuyerRatings;

public class RatingsMapper {
  private RatingsMapper() {}

  public static BuyerRatings convertBuyerRatingsRequestToBuyerRatingsModel(
      BuyerRatingsRequest buyerRatingsRequest, int fromUserId) {
    BuyerRatings buyerRatings = new BuyerRatings();

    buyerRatings.setRatingToUserId(buyerRatingsRequest.getRatingToUserId());
    buyerRatings.setRatingFromUserId(fromUserId);
    buyerRatings.setBuyerRatings(buyerRatingsRequest.getBuyerRatings());

    return buyerRatings;
  }
}
