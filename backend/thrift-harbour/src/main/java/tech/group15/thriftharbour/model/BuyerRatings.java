package tech.group15.thriftharbour.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "buyer_ratings")
public class BuyerRatings {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int buyerRatingID;

  private int ratingToUserId;

  private int ratingFromUserId;

  private int buyerRatings;
}
