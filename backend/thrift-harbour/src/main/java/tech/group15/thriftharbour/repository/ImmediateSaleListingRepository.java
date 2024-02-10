package tech.group15.thriftharbour.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.group15.thriftharbour.model.ImmediateSaleListing;

public interface ImmediateSaleListingRepository extends JpaRepository<ImmediateSaleListing, String> {
}
