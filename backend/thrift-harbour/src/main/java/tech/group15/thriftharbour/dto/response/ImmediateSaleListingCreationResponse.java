package tech.group15.thriftharbour.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;


@Data
@Builder
public class ImmediateSaleListingCreationResponse {
    private String immediateSaleListingID;

    private String productName;

    private String productDescription;

    private double price;

    private String category;

    private String sellerEmail;

    private List<String> imageURLs;

    private boolean active;

    private boolean isApproved;

    private boolean isRejected;

    private Date createdDate;
}
