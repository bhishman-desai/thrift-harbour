package tech.group15.thriftharbour.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.group15.thriftharbour.dto.ListingReviewRequest;
import tech.group15.thriftharbour.dto.ListingReviewResponse;
import tech.group15.thriftharbour.repository.AuctionSaleListingRepository;
import tech.group15.thriftharbour.repository.ImmediateSaleImageRepository;
import tech.group15.thriftharbour.repository.ImmediateSaleListingRepository;
import tech.group15.thriftharbour.service.AdminService;
import tech.group15.thriftharbour.service.JWTService;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final JWTService jwtService;

    private final ImmediateSaleListingRepository immediateSaleListingRepository;

    private final AuctionSaleListingRepository auctionSaleListingRepository;


    @Override
    public ListingReviewResponse reviewListing(String authorizationHeader, ListingReviewRequest reviewRequest) {
        String adminEmail = jwtService.extractUserNameFromRequestHeaders(authorizationHeader);
        return null;
    }
}
