package tech.group15.thriftharbour.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.group15.thriftharbour.enums.RoleEnum;
import tech.group15.thriftharbour.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
  Optional<User> findByEmail(String email);

  User findByRole(RoleEnum role);

  boolean existsByEmail(String email);
}
