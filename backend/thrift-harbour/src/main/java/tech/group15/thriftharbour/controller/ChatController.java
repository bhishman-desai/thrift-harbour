package tech.group15.thriftharbour.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import tech.group15.thriftharbour.dto.ChatNotification;
import tech.group15.thriftharbour.model.ChatMessage;
import tech.group15.thriftharbour.service.ChatMessageService;

/** Controller class for handling chat-related endpoints and WebSocket messaging. */
@RestController
@RequiredArgsConstructor
public class ChatController {
  private final SimpMessagingTemplate simpMessagingTemplate;
  private final ChatMessageService chatMessageService;

  /**
   * Handles WebSocket message processing for the "/chat" endpoint.
   *
   * @param chatMessage The incoming {@code ChatMessage} payload.
   */
  @MessageMapping("/chat")
  public void processMessage(@Payload ChatMessage chatMessage) {
    ChatMessage savedMessage = chatMessageService.save(chatMessage);
    simpMessagingTemplate.convertAndSendToUser(
        chatMessage.getRecipientId(),
        "/queue/messages", /* @SentTo path */
        ChatNotification.builder()
            .id(savedMessage.getChatId())
            .senderId(savedMessage.getSenderId())
            .recipientId(savedMessage.getRecipientId())
            .content(savedMessage.getContent())
            .build());
  }

  /**
   * Handles HTTP GET request for retrieving chat messages between two users.
   *
   * @param senderId The ID of the sender user.
   * @param recipientId The ID of the recipient user.
   * @return A {@code ResponseEntity} containing a list of {@code ChatMessage} objects.
   */
  @GetMapping("messages/{senderId}/{recipientId}")
  public ResponseEntity<List<ChatMessage>> findChatMessages(
      @PathVariable("senderId") String senderId, @PathVariable("recipientId") String recipientId) {
    return ResponseEntity.ok(chatMessageService.findChatMessages(senderId, recipientId));
  }
}
