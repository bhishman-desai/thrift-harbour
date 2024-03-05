package tech.group15.thriftharbour.service.implementation;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.group15.thriftharbour.model.ChatRoom;
import tech.group15.thriftharbour.repository.ChatRoomRepository;
import tech.group15.thriftharbour.service.ChatRoomService;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {

  private final ChatRoomRepository chatRoomRepository;

  /**
   * Gets the chat room ID for the given sender and recipient users. Optionally, creates a new chat
   * room if it doesn't exist.
   *
   * @param senderId The ID of the sender user.
   * @param recipientId The ID of the recipient user.
   * @param createNewRoomIfNotExists If true, creates a new chat room if it doesn't exist.
   * @return An optional containing the chat room ID if found or created, otherwise empty.
   */
  @Override
  public Optional<String> getChatRoomId(
      String senderId, String recipientId, boolean createNewRoomIfNotExists) {
    return chatRoomRepository
        .findBySenderIdAndRecipientId(senderId, recipientId)
        .map(ChatRoom::getChatId)
        .or(
            () -> {
              if (createNewRoomIfNotExists) {
                var chatId = createChatId(senderId, recipientId);
                return Optional.of(chatId);
              }

              return Optional.empty();
            });
  }

  /**
   * Creates a new chat room ID based on the sender and recipient IDs. Also, saves two ChatRoom
   * entities for bidirectional association.
   *
   * @param senderId The ID of the sender user.
   * @param recipientId The ID of the recipient user.
   * @return The created chat room ID.
   */
  private String createChatId(String senderId, String recipientId) {
    var chatId = String.format("%s_%s", senderId, recipientId); // sender_receiver
    ChatRoom senderRecipient =
        ChatRoom.builder().chatId(chatId).senderId(senderId).recipientId(recipientId).build();
    ChatRoom recipientSender =
        ChatRoom.builder().chatId(chatId).senderId(recipientId).recipientId(senderId).build();

    chatRoomRepository.save(senderRecipient);
    chatRoomRepository.save(recipientSender);

    return chatId;
  }
}
