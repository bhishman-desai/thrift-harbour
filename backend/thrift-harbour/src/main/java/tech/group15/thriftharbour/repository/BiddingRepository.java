package tech.group15.thriftharbour.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.group15.thriftharbour.model.Bidding;

public interface BiddingRepository extends JpaRepository<Bidding, Integer> {

}
