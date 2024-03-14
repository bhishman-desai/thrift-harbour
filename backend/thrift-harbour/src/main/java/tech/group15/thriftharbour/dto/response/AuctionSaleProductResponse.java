package tech.group15.thriftharbour.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AuctionSaleProductResponse {

    private String auctionSaleListingID;

    private String productName;

    private String productDescription;

    private double startingBid;

    private double highestBid;

    private List<String> imageURLs;

    private String sellerName;
}

