package tech.group15.thriftharbour.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.group15.thriftharbour.model.BuyerRatings;
import tech.group15.thriftharbour.model.User;

public interface BuyerRatingsRepository extends JpaRepository<BuyerRatings, Integer> {

}
