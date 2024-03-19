package tech.group15.thriftharbour.service;

import tech.group15.thriftharbour.dto.request.ListingReviewRequest;
import tech.group15.thriftharbour.dto.response.ListingReviewResponse;

public interface AdminService {

    ListingReviewResponse reviewListing(String authorizationHeader, ListingReviewRequest reviewRequest);

}
