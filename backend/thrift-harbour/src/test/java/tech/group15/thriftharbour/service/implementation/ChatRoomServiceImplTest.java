package tech.group15.thriftharbour.service.implementation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tech.group15.thriftharbour.model.ChatRoom;
import tech.group15.thriftharbour.repository.ChatRoomRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Test class for {@link ChatRoomServiceImpl}.
 */
public class ChatRoomServiceImplTest {

    @Mock
    private ChatRoomRepository chatRoomRepository;

    @InjectMocks
    private ChatRoomServiceImpl chatRoomService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test case for {@link ChatRoomServiceImpl#getChatRoomId(String, String, boolean)} when the chat room already exists.
     */
    @Test
    public void testGetChatRoomIdWithExistingRoom() {
        /* Arrange */
        String senderId = "1";
        String recipientId = "2";
        String chatId = senderId + "_" + recipientId;
        ChatRoom chatRoom = ChatRoom.builder().chatId(chatId).senderId(senderId).recipientId(recipientId).build();

        /* Act */
        when(chatRoomRepository.findBySenderIdAndRecipientId(senderId, recipientId)).thenReturn(Optional.of(chatRoom));
        Optional<String> result = chatRoomService.getChatRoomId(senderId, recipientId, false);

        /* Assert */
        verify(chatRoomRepository, times(1)).findBySenderIdAndRecipientId(senderId, recipientId);
        assertEquals(Optional.of(chatId), result);
    }

    /**
     * Test case for {@link ChatRoomServiceImpl#getChatRoomId(String, String, boolean)} when the chat room does not exist and a new one is created.
     */
    @Test
    public void testGetChatRoomIdWithNoExistingRoom() {
        /* Arrange */
        String senderId = "1";
        String recipientId = "2";
        String chatId = senderId + "_" + recipientId;

        /* Act */
        when(chatRoomRepository.findBySenderIdAndRecipientId(senderId, recipientId)).thenReturn(Optional.empty());
        Optional<String> result = chatRoomService.getChatRoomId(senderId, recipientId, true);

        /* Assert */
        verify(chatRoomRepository, times(1)).findBySenderIdAndRecipientId(senderId, recipientId);
        verify(chatRoomRepository, times(2)).save(any(ChatRoom.class)); // Two chat rooms are saved for bidirectional association
        assertEquals(Optional.of(chatId), result);
    }

    /**
     * Test case for {@link ChatRoomServiceImpl#getChatRoomId(String, String, boolean)} when the chat room does not exist and a new one is not created.
     */
    @Test
    public void testGetChatRoomIdWithNewRoom() {
        /* Arrange */
        String senderId = "1";
        String recipientId = "2";

        /* Act */
        when(chatRoomRepository.findBySenderIdAndRecipientId(senderId, recipientId)).thenReturn(Optional.empty());
        Optional<String> result = chatRoomService.getChatRoomId(senderId, recipientId, false);

        /* Assert */
        verify(chatRoomRepository, times(1)).findBySenderIdAndRecipientId(senderId, recipientId);
        assertEquals(Optional.empty(), result);
    }

}
