package tech.group15.thriftharbour.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.group15.thriftharbour.model.AuctionSaleListing;

import java.util.List;

public interface AuctionSaleListingRepository extends JpaRepository<AuctionSaleListing, String> {

    List<AuctionSaleListing> findAllBySellerEmail(String sellerEmail);
}
