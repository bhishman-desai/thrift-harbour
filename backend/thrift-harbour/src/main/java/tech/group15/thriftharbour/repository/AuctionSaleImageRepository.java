package tech.group15.thriftharbour.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.group15.thriftharbour.model.AuctionSaleImage;

import java.util.List;

public interface AuctionSaleImageRepository extends JpaRepository<AuctionSaleImage, Integer> {


    List<AuctionSaleImage> findAllByAuctionSaleListingID(String auctionSaleListingID);
}
