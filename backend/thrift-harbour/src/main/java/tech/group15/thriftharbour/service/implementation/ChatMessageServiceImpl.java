package tech.group15.thriftharbour.service.implementation;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.group15.thriftharbour.model.ChatMessage;
import tech.group15.thriftharbour.repository.ChatMessageRepository;
import tech.group15.thriftharbour.service.ChatMessageService;
import tech.group15.thriftharbour.service.ChatRoomService;

@Service
@RequiredArgsConstructor
public class ChatMessageServiceImpl implements ChatMessageService {
  private final ChatMessageRepository chatMessageRepository;
  private final ChatRoomService chatRoomService;

  /**
   * Saves a chat message to the database.
   *
   * @param chatMessage The {@code ChatMessage} to be saved.
   * @return The saved {@code ChatMessage} object.
   */
  @Override
  public ChatMessage save(ChatMessage chatMessage) {
    /* Get or create a chat room and set its ID in the chat message */
    var chatId =
        chatRoomService
            .getChatRoomId(chatMessage.getSenderId(), chatMessage.getRecipientId(), true)
            .orElseThrow();

    chatMessage.setChatId(chatId);
    chatMessageRepository.save(chatMessage);
    return chatMessage;
  }

  /**
   * Retrieves a list of chat messages between two users.
   *
   * @param senderId The ID of the sender user.
   * @param recipientId The ID of the recipient user.
   * @return A list of {@code ChatMessage} objects.
   */
  @Override
  public List<ChatMessage> findChatMessages(String senderId, String recipientId) {
    /* Get the chat room ID and retrieve chat messages for the given chat room */
    var chatId = chatRoomService.getChatRoomId(senderId, recipientId, false);
    return chatId.map(chatMessageRepository::findByChatId).orElse(new ArrayList<>());
  }
}
