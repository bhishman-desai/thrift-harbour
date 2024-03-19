package tech.group15.thriftharbour.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Bidding {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int bidId;

  private String auctionSaleListingID;

  private String userEmail;

  private double placedBid;
}
