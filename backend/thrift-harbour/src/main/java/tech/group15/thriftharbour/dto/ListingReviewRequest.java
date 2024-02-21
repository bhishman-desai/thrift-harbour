package tech.group15.thriftharbour.dto;

import tech.group15.thriftharbour.enums.ListingStatus;
import tech.group15.thriftharbour.enums.SellCategoryEnum;

public class ListingReviewRequest {
    String listingId;

    ListingStatus status;

    SellCategoryEnum sellCategory;

    String message;


}
