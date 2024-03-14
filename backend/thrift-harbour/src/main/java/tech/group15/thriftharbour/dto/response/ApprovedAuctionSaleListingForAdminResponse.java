package tech.group15.thriftharbour.dto.response;

import lombok.Data;

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

    private Date dateOfApproval;

    private boolean isSold;

}
