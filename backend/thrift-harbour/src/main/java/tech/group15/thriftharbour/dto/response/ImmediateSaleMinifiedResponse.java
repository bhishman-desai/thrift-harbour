package tech.group15.thriftharbour.dto.response;

import lombok.Data;

/* All product listing for admin */
@Data
public class ImmediateSaleMinifiedResponse {
  private String immediateSaleListingID;

  private String productName;

  private double price;

  private boolean active;

  private boolean isApproved;

  private boolean isRejected;
}
