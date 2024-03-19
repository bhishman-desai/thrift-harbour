package tech.group15.thriftharbour.dto.response;

import lombok.Data;

@Data
public class SignInResponse {
  private String token;
  private String refreshToken;
}
