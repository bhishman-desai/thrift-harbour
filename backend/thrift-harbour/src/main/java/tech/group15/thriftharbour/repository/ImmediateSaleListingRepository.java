package tech.group15.thriftharbour.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tech.group15.thriftharbour.model.ImmediateSaleListing;

import java.util.List;

public interface ImmediateSaleListingRepository extends JpaRepository<ImmediateSaleListing, String> {

    List<ImmediateSaleListing> findAllBySellerEmail(String sellerEmail);

    @Query("SELECT i FROM ImmediateSaleListing i WHERE i.seller.userID = ?1")
    List<ImmediateSaleListing> findAllBySellerID(Integer sellerID);

    ImmediateSaleListing findByImmediateSaleListingID(String immediateSaleListingID);

    @Query("SELECT i FROM ImmediateSaleListing i WHERE i.active = true and i.isApproved = true and i.isRejected = false")
    List<ImmediateSaleListing> findAllApprovedImmediateSaleListing();

    @Query("SELECT i FROM ImmediateSaleListing i WHERE i.active = true and i.isApproved = false and i.isRejected = true")
    List<ImmediateSaleListing> findAllDeniedImmediateSaleListing();

    @Query("SELECT i FROM ImmediateSaleListing i WHERE i.sellerEmail != ?1 and i.active = true and i.isApproved = true and i.isRejected = false")
    List<ImmediateSaleListing> findAllImmediateSaleListing(String sellerEmail);

}
