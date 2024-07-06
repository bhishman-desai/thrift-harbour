package tech.group15.thriftharbour.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.group15.thriftharbour.model.ImmediateSaleImage;

import java.util.List;

public interface ImmediateSaleImageRepository extends JpaRepository<ImmediateSaleImage, Integer> {

    List<ImmediateSaleImage> getAllByImmediateSaleListingID(String immediateSaleListingID);
}
