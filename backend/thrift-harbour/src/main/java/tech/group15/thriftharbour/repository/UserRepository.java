package tech.group15.thriftharbour.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import tech.group15.thriftharbour.enums.RoleEnum;
import tech.group15.thriftharbour.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
  Optional<User> findByEmail(String email);

  User findByRole(RoleEnum role);

  boolean existsByEmail(String email);

  @Query(value = "SELECT u.* FROM user u JOIN immediate_sale_listing isl ON u.userid = isl.seller_id", nativeQuery = true)
  List<User> findAllSellers();

  @Transactional
  @Modifying
  @Query("UPDATE User u SET u.avgBuyerRatings = ?1 WHERE u.userID = ?2")
  void updateBuyerRatings(double avgBuyerRatings, int userID);

  @Transactional
  @Modifying
  @Query("UPDATE User u SET u.avgSellerRatings = ?1 WHERE u.userID = ?2")
  void updateSellerRatings(double avgSellerRatings, int userID);
}
