package tech.group15.thriftharbour.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tech.group15.thriftharbour.model.AuctionSaleListing;
import tech.group15.thriftharbour.model.ImmediateSaleListing;

import java.util.Date;
import java.util.List;

public interface AuctionSaleListingRepository extends JpaRepository<AuctionSaleListing, String> {

    List<AuctionSaleListing> findAllBySellerEmail(String sellerEmail);

    AuctionSaleListing findByAuctionSaleListingID(String auctionSaleListingID);

    @Query("SELECT i FROM AuctionSaleListing i WHERE i.active = true and i.isApproved = true and i.isRejected = false")
    List<AuctionSaleListing> findAllApprovedAuctionSaleListing();

    @Query("SELECT i FROM AuctionSaleListing i WHERE i.active = true and i.isApproved = false and i.isRejected = true")
    List<AuctionSaleListing> findAllDeniedAuctionSaleListing();

    @Query("SELECT i FROM AuctionSaleListing i WHERE i.auctionSaleListingID = ?1 and i.active = true and i.isApproved = true and i.isRejected = false and i.isSold = false")
    AuctionSaleListing findAuctionSaleProductByID(String auctionSaleListingID);

    @Query("SELECT i FROM AuctionSaleListing i WHERE i.sellerEmail != ?1 and CAST(auctionSlot AS date) = ?2 and i.active = true and i.isApproved = true and i.isRejected = false")
    List<AuctionSaleListing> findAllAuctionSaleListing(String sellerEmail, Date auctionSlot);

    @Query("SELECT i FROM AuctionSaleListing i WHERE i.active = true")
    List<AuctionSaleListing> findAllAuctionSaleListingForAdmin();
}
