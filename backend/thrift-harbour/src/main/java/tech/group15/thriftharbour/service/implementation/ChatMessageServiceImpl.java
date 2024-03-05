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

  @Override
  public ChatMessage save(ChatMessage chatMessage) {
    var chatId =
        chatRoomService
            .getChatRoomId(chatMessage.getSenderId(), chatMessage.getRecipientId(), true)
            .orElseThrow();

    chatMessage.setChatId(chatId);
    chatMessageRepository.save(chatMessage);
    return chatMessage;
  }

  @Override
  public List<ChatMessage> findChatMessages(String senderId, String recipientId) {
    var chatId = chatRoomService.getChatRoomId(senderId, recipientId, false);
    return chatId.map(chatMessageRepository::findByChatId).orElse(new ArrayList<>());
  }
}
