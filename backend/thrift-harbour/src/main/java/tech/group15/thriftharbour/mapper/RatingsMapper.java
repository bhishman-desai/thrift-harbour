package tech.group15.thriftharbour.mapper;

import tech.group15.thriftharbour.dto.request.BuyerRatingsRequest;
import tech.group15.thriftharbour.dto.request.SellerRatingsRequest;
import tech.group15.thriftharbour.model.BuyerRatings;
import tech.group15.thriftharbour.model.SellerRatings;

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

  public static SellerRatings convertSellerRatingsRequestToSellerRatingsModel(
      SellerRatingsRequest sellerRatingsRequest, int fromUserId) {
    SellerRatings sellerRatings = new SellerRatings();

    sellerRatings.setRatingToUserId(sellerRatingsRequest.getRatingToUserId());
    sellerRatings.setRatingFromUserId(fromUserId);
    sellerRatings.setSellerRatings(sellerRatingsRequest.getSellerRatings());

    return sellerRatings;
  }
}
