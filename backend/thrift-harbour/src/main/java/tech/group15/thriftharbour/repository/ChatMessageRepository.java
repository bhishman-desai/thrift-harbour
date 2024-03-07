package tech.group15.thriftharbour.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tech.group15.thriftharbour.model.ChatMessage;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Integer> {
  List<ChatMessage> findByChatId(String chatId);

  @Query("SELECT DISTINCT c.senderId FROM ChatMessage c WHERE c.recipientId = :recipientId")
  List<Integer> findDistinctSenderIdsByRecipientId(@Param("recipientId") String recipientId);
}
