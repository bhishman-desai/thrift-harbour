package tech.group15.thriftharbour.service;

import tech.group15.thriftharbour.model.ChatMessage;

import java.util.List;

public interface ChatMessageService {
  ChatMessage save(ChatMessage chatMessage);

  List<ChatMessage> findChatMessages(String senderId, String recipientId);
  List<Integer> findSenderByRecipientId(String recipientID);
}
