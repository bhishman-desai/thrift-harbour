package tech.group15.thriftharbour.dto;

import lombok.Data;

@Data
public class ResetPassRequest {
    private String token;
    private String password;
}
