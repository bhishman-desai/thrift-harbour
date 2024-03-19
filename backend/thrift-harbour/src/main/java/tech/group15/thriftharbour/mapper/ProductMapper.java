package tech.group15.thriftharbour.mapper;

import java.util.ArrayList;
import java.util.List;
import tech.group15.thriftharbour.dto.ImmediateSaleMinifiedResponse;
import tech.group15.thriftharbour.model.ImmediateSaleListing;

public class ProductMapper {
  private ProductMapper() {}

  public static List<ImmediateSaleMinifiedResponse> minifiedProductResponse(
          List<ImmediateSaleListing> immediateSaleListings) {
    List<ImmediateSaleMinifiedResponse> minifiedResponses = new ArrayList<>();
    for (ImmediateSaleListing immediateSaleListing : immediateSaleListings) {
      ImmediateSaleMinifiedResponse immediateSaleMinifiedResponse =
              new ImmediateSaleMinifiedResponse();
      immediateSaleMinifiedResponse.setImmediateSaleListingID(
              immediateSaleListing.getImmediateSaleListingID());
      immediateSaleMinifiedResponse.setProductName(immediateSaleListing.getProductName());
      immediateSaleMinifiedResponse.setPrice(immediateSaleListing.getPrice());
      immediateSaleMinifiedResponse.setActive(immediateSaleListing.isActive());
      immediateSaleMinifiedResponse.setApproved(immediateSaleListing.isApproved());
      immediateSaleMinifiedResponse.setRejected(immediateSaleListing.isRejected());

      minifiedResponses.add(immediateSaleMinifiedResponse);
    }

    return minifiedResponses;
  }
}
