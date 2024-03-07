package tech.group15.thriftharbour.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import tech.group15.thriftharbour.dto.SellerResponse;
import tech.group15.thriftharbour.model.User;

import java.util.List;

public interface UserService {
  UserDetailsService userDetailsService();

  List<SellerResponse> findAllSellers();
  User findUserById(Integer userID);
  List<User> findSenderByRecipientId(String recipientID);
}
