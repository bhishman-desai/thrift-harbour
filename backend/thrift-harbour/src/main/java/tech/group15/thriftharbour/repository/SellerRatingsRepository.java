package tech.group15.thriftharbour.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tech.group15.thriftharbour.model.SellerRatings;

public interface SellerRatingsRepository extends JpaRepository<SellerRatings, Integer> {
  @Query("SELECT AVG(s.sellerRatings) FROM SellerRatings s WHERE s.ratingToUserId = ?1")
  double findAvgSellerRatings(Integer userId);
}
