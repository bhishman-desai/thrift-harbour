package tech.group15.thriftharbour.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.group15.thriftharbour.model.BuyerRatings;

public interface BuyerRatingsRepository extends JpaRepository<BuyerRatings, Integer> {

}
