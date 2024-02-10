package tech.group15.thriftharbour.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.group15.thriftharbour.model.AuctionSaleListing;

public interface AuctionSaleListingRepository extends JpaRepository<AuctionSaleListing, String> {
}
