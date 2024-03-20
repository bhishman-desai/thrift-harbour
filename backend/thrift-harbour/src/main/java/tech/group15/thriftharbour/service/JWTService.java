package tech.group15.thriftharbour.service;

import java.util.HashMap;
import org.springframework.security.core.userdetails.UserDetails;
import tech.group15.thriftharbour.dto.request.RefreshTokenRequest;

public interface JWTService {
  String extractUserName(String token);

  String extractUserNameFromRequestHeaders(String authorizationHeader);

  String generateToken(UserDetails userDetails);

  String generateRefreshToken(HashMap<String, Object> objectObjectHashMap, UserDetails userDetails);

  boolean isTokenValid(String token, UserDetails userDetails);

  String onRefreshToken(RefreshTokenRequest refreshTokenRequest, UserDetails userDetails);
}
