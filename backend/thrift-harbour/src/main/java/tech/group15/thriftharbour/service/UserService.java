package tech.group15.thriftharbour.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import tech.group15.thriftharbour.dto.SellerResponse;

import java.util.List;

public interface UserService {
  UserDetailsService userDetailsService();

  List<SellerResponse> findAllSellers();
}
