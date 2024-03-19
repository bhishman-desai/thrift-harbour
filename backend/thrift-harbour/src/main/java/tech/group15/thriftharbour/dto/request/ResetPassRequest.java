package tech.group15.thriftharbour.dto.request;

import lombok.Data;

@Data
public class ResetPassRequest {
    private String token;
    private String password;
}
