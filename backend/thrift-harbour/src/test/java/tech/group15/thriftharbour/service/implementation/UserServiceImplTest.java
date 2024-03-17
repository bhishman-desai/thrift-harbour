package tech.group15.thriftharbour.service.implementation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import tech.group15.thriftharbour.dto.response.SellerResponse;
import tech.group15.thriftharbour.enums.RoleEnum;
import tech.group15.thriftharbour.model.User;
import tech.group15.thriftharbour.repository.UserRepository;
import tech.group15.thriftharbour.service.ChatMessageService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
class UserServiceImplTest {
    @Mock
    UserRepository userRepository;
    @Mock
    ChatMessageService chatMessageService;
    @InjectMocks
    UserServiceImpl userServiceImpl;
    String userEmail;
    User user;
    Integer userId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userId = 1;
        userEmail = "user@gmail.com";
        user = new User(1, "firstName", "lastName", userEmail, "password", RoleEnum.USER, 4.0, 5.0);
    }

    /**
     * Tests the fetching user details.
     */
    @Test
    void testUserDetailsService(){
        when(userRepository.findByEmail(userEmail)).thenReturn(Optional.of(user));

        UserDetailsService result = userServiceImpl.userDetailsService();
        UserDetails userDetails = result.loadUserByUsername(userEmail);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(userEmail, userDetails.getUsername());
        verify(userRepository).findByEmail(userEmail);
    }

    /**
     * Tests user not found while fetching user details.
     */
    @Test
    void userDetailsService_UserNotFound() {
        when(userRepository.findByEmail(userEmail)).thenReturn(Optional.empty());

        UserDetailsService userDetailsService = userServiceImpl.userDetailsService();

        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername(userEmail));
        verify(userRepository).findByEmail(userEmail);
    }

    /**
     * Tests the find all sellers details.
     */
    @Test
    void testFindAllSellers(){
        when(userRepository.findAllSellers()).thenReturn(List.of(user));

        List<SellerResponse> result = userServiceImpl.findAllSellers();

        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.isEmpty());
        verify(userRepository).findAllSellers();
    }

    /**
     * Tests the fetching user details by user id.
     */
    @Test
    void testFindUserById(){
        int userId = 1;
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));

        User result = userServiceImpl.findUserById(userId);
        Assertions.assertEquals(user, result);
        verify(userRepository).findById(userId);
    }

    /**
     * Tests the user not found while fetching user details by user id.
     */
    @Test
    void findUserById_NotFound() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userServiceImpl.findUserById(userId));
        verify(userRepository).findById(userId);
    }

    /**
     * Tests the retrievals of senders list based on recipient id.
     */
    @Test
    void testFindSenderByRecipientId(){
        when(chatMessageService.findSenderByRecipientId(anyString())).thenReturn(List.of(1));
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));

        List<User> result = userServiceImpl.findSenderByRecipientId("recipientID");
        Assertions.assertEquals(List.of(user), result);
        verify(chatMessageService).findSenderByRecipientId("recipientID");
        verify(userRepository).findById(userId);
    }
}
