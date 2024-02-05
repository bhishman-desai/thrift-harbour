package tech.group15.thriftharbour.dto;

import lombok.Data;

@Data
public class SignInRequest {
  private String email;
  private String password;
}
