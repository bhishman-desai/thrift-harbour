package tech.group15.thriftharbour.service.implementation;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tech.group15.thriftharbour.dto.response.SellerResponse;
import tech.group15.thriftharbour.mapper.UserMapper;
import tech.group15.thriftharbour.model.User;
import tech.group15.thriftharbour.repository.UserRepository;
import tech.group15.thriftharbour.service.ChatMessageService;
import tech.group15.thriftharbour.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final ChatMessageService chatMessageService;

  /**
   * This method provides a UserDetailsService implementation using a lambda function.
   *
   * @return UserDetailsService - A UserDetailsService that loads a user by their email.
   */
  public UserDetailsService userDetailsService() {
    return username ->
        userRepository
            .findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not Found!"));
  }

  /**
   * Retrieves a list of SellerResponse objects representing information about all sellers.
   *
   * @return A List of SellerResponse objects containing information about all sellers.
   */
  @Override
  public List<SellerResponse> findAllSellers() {
    return UserMapper.generateSellerInformationResponse(userRepository.findAllSellers());
  }

  /**
   * Retrieves a User object based on the provided user ID.
   *
   * @param userID The ID of the user to be retrieved.
   * @return The User object associated with the given user ID.
   * @throws UsernameNotFoundException If no user is found with the provided user ID.
   */
  @Override
  public User findUserById(Integer userID) {
    return userRepository
        .findById(userID)
        .orElseThrow(() -> new UsernameNotFoundException("User not Found!"));
  }

  /**
   * Retrieves a list of User objects representing recipients based on the sender's ID.
   *
   * @param recipientID The ID of the sender for whom recipients need to be found.
   * @return A List of User objects representing recipients associated with the given sender ID.
   */
  @Override
  public List<User> findSenderByRecipientId(String recipientID) {
    List<Integer> senderIds = chatMessageService.findSenderByRecipientId(recipientID);
    List<User> senders = new ArrayList<>();

    for (Integer senderId : senderIds) {
      senders.add(findUserById(senderId));
    }

    return senders;
  }
}
