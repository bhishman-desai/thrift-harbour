package tech.group15.thriftharbour.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ApprovedImmediateSaleListingForAdminResponse {
    private String immediateSaleListingID;

    private String productName;

    private String productDescription;

    private double price;

    private String category;

    private String sellerEmail;

    private List<String> imageURLs;

    private boolean active;

    private boolean isApproved;

    private String approverEmail;

    private String messageFromApprover;

    private Date dateOfApproval;

    private boolean isSold;

}
