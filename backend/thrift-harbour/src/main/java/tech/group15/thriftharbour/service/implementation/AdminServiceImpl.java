package tech.group15.thriftharbour.service.implementation;

import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.group15.thriftharbour.dto.ListingReviewRequest;
import tech.group15.thriftharbour.dto.ListingReviewResponse;
import tech.group15.thriftharbour.enums.ListingStatus;
import tech.group15.thriftharbour.enums.SellCategoryEnum;
import tech.group15.thriftharbour.exception.ListingNotActiveException;
import tech.group15.thriftharbour.exception.ListingNotFoundException;
import tech.group15.thriftharbour.model.AuctionSaleListing;
import tech.group15.thriftharbour.model.ImmediateSaleListing;
import tech.group15.thriftharbour.repository.AuctionSaleListingRepository;
import tech.group15.thriftharbour.repository.ImmediateSaleListingRepository;
import tech.group15.thriftharbour.service.AdminService;
import tech.group15.thriftharbour.service.JWTService;
import tech.group15.thriftharbour.utils.DateUtil;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

  private final JWTService jwtService;

  private final ImmediateSaleListingRepository immediateSaleListingRepository;

  private final AuctionSaleListingRepository auctionSaleListingRepository;

  @Override
  public ListingReviewResponse reviewListing(
      String authorizationHeader, ListingReviewRequest reviewRequest) {

    String adminEmail = jwtService.extractUserNameFromRequestHeaders(authorizationHeader);

    if (reviewRequest.getSellCategory().equals(SellCategoryEnum.DIRECT))
      return setStatusOfImmediateSaleListing(adminEmail, reviewRequest);
    else return setStatusOfAuctionSaleListing(adminEmail, reviewRequest);
  }

  private ListingReviewResponse setStatusOfImmediateSaleListing(
      String adminEmail, ListingReviewRequest reviewRequest) {

    ImmediateSaleListing immediateSaleListing =
        immediateSaleListingRepository.findByImmediateSaleListingID(reviewRequest.getListingId());

    Date currentDate = DateUtil.getCurrentDate();

    if (immediateSaleListing == null)
      throw new ListingNotFoundException(
          String.format(
              "No listing found with provided listing id:%s", reviewRequest.getListingId()));
    else if (!immediateSaleListing.isActive()) {
      throw new ListingNotActiveException("Not an active listing");
    }

    ListingReviewResponse listingReviewResponse =
        ListingReviewResponse.builder()
            .listingId(immediateSaleListing.getImmediateSaleListingID())
            .build();

    if (reviewRequest.getStatus().equals(ListingStatus.APPROVED)) {
      immediateSaleListing.setApproved(true);
      immediateSaleListing.setRejected(false);
      listingReviewResponse.setStatus(ListingStatus.APPROVED.toString());
    } else {
      immediateSaleListing.setApproved(false);
      immediateSaleListing.setRejected(true);
      listingReviewResponse.setStatus(ListingStatus.REJECTED.toString());
    }

    immediateSaleListing.setApproverEmail(adminEmail);
    immediateSaleListing.setMessageFromApprover(reviewRequest.getMessage());
    immediateSaleListing.setDateOfApproval(currentDate);

    immediateSaleListingRepository.save(immediateSaleListing);

    return listingReviewResponse;
  }

  private ListingReviewResponse setStatusOfAuctionSaleListing(
      String adminEmail, ListingReviewRequest reviewRequest) {

    AuctionSaleListing auctionSaleListing =
        auctionSaleListingRepository.findByAuctionSaleListingID(reviewRequest.getListingId());

    Date currentDate = DateUtil.getCurrentDate();

    if (auctionSaleListing == null)
      throw new ListingNotFoundException(
          String.format(
              "No listing found with provided listing id:%s", reviewRequest.getListingId()));
    else if (!auctionSaleListing.isActive()) {
      throw new ListingNotActiveException("Not an active listing");
    }

    ListingReviewResponse listingReviewResponse =
        ListingReviewResponse.builder()
            .listingId(auctionSaleListing.getAuctionSaleListingID())
            .build();

    if (reviewRequest.getStatus().equals(ListingStatus.APPROVED)) {
      auctionSaleListing.setApproved(true);
      auctionSaleListing.setRejected(false);
      listingReviewResponse.setStatus(ListingStatus.APPROVED.toString());
    } else {
      auctionSaleListing.setApproved(false);
      auctionSaleListing.setRejected(true);
      listingReviewResponse.setStatus(ListingStatus.REJECTED.toString());
    }

    auctionSaleListing.setApproverEmail(adminEmail);
    auctionSaleListing.setMessageFromApprover(reviewRequest.getMessage());
    auctionSaleListing.setDateOfApproval(currentDate);

    auctionSaleListingRepository.save(auctionSaleListing);

    return ListingReviewResponse.builder()
        .listingId(auctionSaleListing.getAuctionSaleListingID())
        .status(ListingStatus.REJECTED.toString())
        .build();
  }
}
