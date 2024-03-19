package tech.group15.thriftharbour.dto;

import lombok.Data;

@Data
public class SellerResponse {
    private int userID;
    
    private String firstName;

    private String lastName;

    private String email;
}
