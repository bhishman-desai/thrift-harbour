package tech.group15.thriftharbour.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.group15.thriftharbour.model.AuctionSaleImage;

public interface AuctionSaleImageRepository extends JpaRepository<AuctionSaleImage, Integer> {
}
