package tech.group15.thriftharbour.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "seller_ratings")
public class SellerRatings {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int sellerRatingID;

  private int ratingToUserId;

  private int ratingFromUserId;

  private int sellerRatings;
}
