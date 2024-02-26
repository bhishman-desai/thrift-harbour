package tech.group15.thriftharbour.service;

import tech.group15.thriftharbour.dto.ListingReviewRequest;
import tech.group15.thriftharbour.dto.ListingReviewResponse;

public interface AdminService {

    ListingReviewResponse reviewListing(String authorizationHeader, ListingReviewRequest reviewRequest);

}
