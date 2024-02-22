package tech.group15.thriftharbour.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import tech.group15.thriftharbour.enums.ListingStatus;
import tech.group15.thriftharbour.enums.SellCategoryEnum;

@Data
public class ListingReviewRequest {

    @NotBlank(message = "Listing ID cannot be empty")
    String listingId;

    @NotBlank(message = "Status cannot be empty")
    ListingStatus status;

    @NotBlank(message = "Sell category should be AUCTION/DIRECT")
    SellCategoryEnum sellCategory;

    @Nullable
    String message;


}
