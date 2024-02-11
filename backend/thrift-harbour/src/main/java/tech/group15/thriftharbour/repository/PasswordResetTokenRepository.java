package tech.group15.thriftharbour.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.group15.thriftharbour.model.PasswordResetToken;
import tech.group15.thriftharbour.model.User;

import java.util.Optional;

@Repository
@Transactional
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Integer> {
    Optional<PasswordResetToken> findByToken(String token);
    Optional<PasswordResetToken> findByUser(User user);
    void deleteAllByTokenID(Long tokenID);
}