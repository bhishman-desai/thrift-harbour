package tech.group15.thriftharbour.service.implementation;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tech.group15.thriftharbour.dto.response.SellerResponse;
import tech.group15.thriftharbour.mapper.UserMapper;
import tech.group15.thriftharbour.model.User;
import tech.group15.thriftharbour.repository.UserRepository;
import tech.group15.thriftharbour.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;

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

  @Override
  public List<SellerResponse> findAllSellers() {
    return UserMapper.generateSellerInformationResponse(userRepository.findAllSellers());
  }

  @Override
  public User findUserById(Integer userID) {
    return userRepository
        .findById(userID)
        .orElseThrow(() -> new UsernameNotFoundException("User not Found!"));
  }
}
