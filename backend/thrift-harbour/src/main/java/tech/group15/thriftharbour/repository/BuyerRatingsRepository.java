package tech.group15.thriftharbour.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tech.group15.thriftharbour.model.BuyerRatings;

public interface BuyerRatingsRepository extends JpaRepository<BuyerRatings, Integer> {
  @Query("SELECT AVG(b.buyerRatings) FROM BuyerRatings b WHERE b.ratingToUserId = ?1")
  double findAvgBuyerRatings(Integer userId);
}
