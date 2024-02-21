package tech.group15.thriftharbour.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
public class ApprovedAuctionSaleListingForAdminResponse {

    private String auctionSaleListingID;

    private String productName;

    private String productDescription;

    private String category;

    private String sellerEmail;

    private List<String> imageURLs;

    private boolean active;

    private boolean isApproved;

    private String approverEmail;

    private String messageFromApprover;

    private LocalDate dateOfApproval;

    private boolean isSold;

}
