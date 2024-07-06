package tech.group15.thriftharbour.service;

import java.util.Optional;

public interface ChatRoomService {
  Optional<String> getChatRoomId(
      String senderId, String recipientId, boolean createNewRoomIfNotExists);
}
