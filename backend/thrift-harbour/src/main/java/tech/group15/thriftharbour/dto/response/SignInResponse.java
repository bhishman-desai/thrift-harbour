package tech.group15.thriftharbour.dto.response;

import lombok.Data;

@Data
public class SignInResponse {
  private int userID;
  private String token;
  private String refreshToken;
}
