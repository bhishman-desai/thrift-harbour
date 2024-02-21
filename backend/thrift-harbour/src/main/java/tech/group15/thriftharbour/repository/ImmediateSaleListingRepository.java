package tech.group15.thriftharbour.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.group15.thriftharbour.model.ImmediateSaleListing;

import java.util.List;

public interface ImmediateSaleListingRepository extends JpaRepository<ImmediateSaleListing, String> {

    List<ImmediateSaleListing> findAllBySellerEmail(String sellerEmail);
}
