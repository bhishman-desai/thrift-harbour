package tech.group15.thriftharbour.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.group15.thriftharbour.model.ImmediateSaleImage;

public interface ImmediateSaleImageRepository extends JpaRepository<ImmediateSaleImage, Integer> {
}
