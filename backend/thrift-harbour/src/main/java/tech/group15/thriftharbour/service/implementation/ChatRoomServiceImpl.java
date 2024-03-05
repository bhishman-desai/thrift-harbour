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
