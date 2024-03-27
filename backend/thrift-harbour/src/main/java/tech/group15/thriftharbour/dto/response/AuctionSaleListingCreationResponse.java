package tech.group15.thriftharbour.dto.response;

import lombok.Builder;
import lombok.Data;
import tech.group15.thriftharbour.model.User;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class AuctionSaleListingCreationResponse {

    private String auctionSaleListingID;

    private String productName;

    private String productDescription;

    private double startingBid;

    private String category;

    private String sellerEmail;

    private Date auctionSlot;

    private List<String> imageURLs;

    private boolean active;

    private boolean isApproved;

    private boolean isRejected;

    private Date createdDate;

    private int sellerID;

    private double highestBid;

    private String currentHighestBidUserMail;

    private User highestBidUser;
}
