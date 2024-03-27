package tech.group15.thriftharbour.dto.request;

import lombok.Data;

@Data
public class SignInRequest {
  private String email;
  private String password;
}
