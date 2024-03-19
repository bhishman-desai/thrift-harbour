package tech.group15.thriftharbour.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ListingReviewResponse {

    String listingId;

    String status;
}
