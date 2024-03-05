package tech.group15.thriftharbour.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import tech.group15.thriftharbour.model.ChatMessage;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Integer> {
  List<ChatMessage> findByChatId(String chatId);
}
