package tech.group15.thriftharbour.dto.request;

import lombok.Data;

@Data
public class RefreshTokenRequest {
  private String token;
}
