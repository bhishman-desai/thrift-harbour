package tech.group15.thriftharbour.dto.response;

import lombok.Builder;
import lombok.Data;
import tech.group15.thriftharbour.model.User;

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

    private User seller;
}

