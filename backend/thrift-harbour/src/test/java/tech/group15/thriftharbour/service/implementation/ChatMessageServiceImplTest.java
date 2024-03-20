package tech.group15.thriftharbour.service.implementation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tech.group15.thriftharbour.model.ChatMessage;
import tech.group15.thriftharbour.repository.ChatMessageRepository;
import tech.group15.thriftharbour.service.ChatRoomService;

import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ChatMessageServiceImplTest {

    @Mock
    private ChatMessageRepository chatMessageRepository;

    @Mock
    private ChatRoomService chatRoomService;

    @InjectMocks
    private ChatMessageServiceImpl chatMessageService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test case for {@link ChatMessageServiceImpl#save(ChatMessage)}.
     */
    @Test
    public void testSave() {
        /* Arrange */
        String senderId = "1";
        String recipientId = "2";
        String chatId = senderId + "_" + recipientId;
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setSenderId(senderId);
        chatMessage.setRecipientId(recipientId);

        when(chatRoomService.getChatRoomId(senderId, recipientId, true)).thenReturn(Optional.of(chatId));
        when(chatMessageRepository.save(chatMessage)).thenReturn(chatMessage);

        /* Act */
        ChatMessage result = chatMessageService.save(chatMessage);

        /* Assert */
        verify(chatRoomService, times(1)).getChatRoomId(senderId, recipientId, true);
        verify(chatMessageRepository, times(1)).save(chatMessage);
        assertEquals(chatId, result.getChatId());
    }

    /**
     * Test case for {@link ChatMessageServiceImpl#findChatMessages(String, String)}.
     */
    @Test
    public void testFindChatMessages() {
        /* Arrange */
        String senderId = "1";
        String recipientId = "2";
        String chatId = senderId + "_" + recipientId;
        List<ChatMessage> chatMessages = new ArrayList<>();

        when(chatRoomService.getChatRoomId(senderId, recipientId, false)).thenReturn(Optional.of(chatId));
        when(chatMessageRepository.findByChatId(chatId)).thenReturn(chatMessages);

        /* Act */
        List<ChatMessage> result = chatMessageService.findChatMessages(senderId, recipientId);

        /* Assert */
        verify(chatRoomService, times(1)).getChatRoomId(senderId, recipientId, false);
        verify(chatMessageRepository, times(1)).findByChatId(chatId);
        assertEquals(chatMessages, result);
    }

    /**
     * Test case for {@link ChatMessageServiceImpl#findSenderByRecipientId(String)}.
     */
    @Test
    public void testFindSenderByRecipientId() {
        /* Arrange */
        String recipientId = "2";
        List<Integer> senderIds = new ArrayList<>();

        when(chatMessageRepository.findDistinctSenderIdsByRecipientId(recipientId)).thenReturn(senderIds);

        /* Act */
        List<Integer> result = chatMessageService.findSenderByRecipientId(recipientId);

        /* Assert */
        verify(chatMessageRepository, times(1)).findDistinctSenderIdsByRecipientId(recipientId);
        assertEquals(senderIds, result);
    }
}
