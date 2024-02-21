package tech.group15.thriftharbour.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.group15.thriftharbour.dto.ListingReviewRequest;
import tech.group15.thriftharbour.dto.ListingReviewResponse;
import tech.group15.thriftharbour.enums.ListingStatus;
import tech.group15.thriftharbour.enums.SellCategoryEnum;
import tech.group15.thriftharbour.exception.ListingNotFoundException;
import tech.group15.thriftharbour.model.AuctionSaleListing;
import tech.group15.thriftharbour.model.ImmediateSaleListing;
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
        if (reviewRequest.getSellCategory().equals(SellCategoryEnum.DIRECT))
            return setStatusOfImmediateSaleListing(adminEmail, reviewRequest);
        else
            return setStatusOfAuctionSaleListing(adminEmail, reviewRequest);
    }


    private ListingReviewResponse setStatusOfImmediateSaleListing(String adminEmail, ListingReviewRequest reviewRequest){
        ImmediateSaleListing immediateSaleListing = immediateSaleListingRepository.findByImmediateSaleListingID(reviewRequest.getListingId());

        if(immediateSaleListing == null)
            throw new ListingNotFoundException(String.format("No listing found with provided listing id:%s", reviewRequest.getListingId()));

        if(reviewRequest.getStatus().equals(ListingStatus.APPROVED)) {
            immediateSaleListing.setApproved(true);
            immediateSaleListing.setRejected(false);
        }
        else {
            immediateSaleListing.setApproved(false);
            immediateSaleListing.setRejected(true);
        }
        immediateSaleListing.setApproverEmail(adminEmail);
        immediateSaleListing.setMessageFromApprover(reviewRequest.getMessage());

        immediateSaleListingRepository.save(immediateSaleListing);

        return ListingReviewResponse
                .builder()
                .listingId(immediateSaleListing.getImmediateSaleListingID())
                .status(ListingStatus.APPROVED.toString())
                .build();

    }

    private ListingReviewResponse setStatusOfAuctionSaleListing(String adminEmail, ListingReviewRequest reviewRequest){
        AuctionSaleListing auctionSaleListing = auctionSaleListingRepository.findByAuctionSaleListingID(reviewRequest.getListingId());

        if(auctionSaleListing == null)
            throw new ListingNotFoundException(String.format("No listing found with provided listing id:%s", reviewRequest.getListingId()));

        if(reviewRequest.getStatus().equals(ListingStatus.REJECTED)){
            auctionSaleListing.setApproved(true);
            auctionSaleListing.setRejected(false);
        }
        else {
            auctionSaleListing.setApproved(false);
            auctionSaleListing.setRejected(true);
        }

        auctionSaleListing.setApproverEmail(adminEmail);
        auctionSaleListing.setMessageFromApprover(reviewRequest.getMessage());

        auctionSaleListingRepository.save(auctionSaleListing);

        return ListingReviewResponse
                .builder()
                .listingId(auctionSaleListing.getAuctionSaleListingID())
                .status(ListingStatus.REJECTED.toString())
                .build();

    }
}
