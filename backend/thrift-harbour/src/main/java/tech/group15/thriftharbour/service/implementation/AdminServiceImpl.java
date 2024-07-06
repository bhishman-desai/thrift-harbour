package tech.group15.thriftharbour.service.implementation;

import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.group15.thriftharbour.dto.request.ListingReviewRequest;
import tech.group15.thriftharbour.dto.response.ListingReviewResponse;
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

  private final ImmediateSaleListingRepository immediateListingRepository;

  private final AuctionSaleListingRepository auctionSaleListingRepository;

  /**
   * Approve/Deny a listing and return the status of listing.
   * @param reviewRequest containing the listing id, status to be updated and sell category
   * @param authorizationHeader from request
   * @return A {@code ListingReviewResponse} object containing the id of listing and status - Approved/Denied
   */
  @Override
  public ListingReviewResponse reviewListing(
      String authorizationHeader, ListingReviewRequest reviewRequest) {

    String adminEmail = jwtService.extractUserNameFromRequestHeaders(authorizationHeader);

    boolean isCategoryDirect = reviewRequest.getSellCategory().equals(SellCategoryEnum.DIRECT);

    ListingReviewResponse response;
    if (isCategoryDirect) {
      response = setStatusOfImmediateSaleListing(adminEmail, reviewRequest);
    } else {
      response = setStatusOfAuctionSaleListing(adminEmail, reviewRequest);
    }

    return response;
  }

  /**
   * Approve/Deny a immediate sale listing and return the status of listing.
   * @param reviewRequest containing the listing id, status to be updated and sell category
   * @param adminEmail email of approver
   * @return A {@code ListingReviewResponse} object containing the id of listing and status - Approved/Denied
   */
  private ListingReviewResponse setStatusOfImmediateSaleListing(
      String adminEmail, ListingReviewRequest reviewRequest) {

    var listingID = reviewRequest.getListingId();

    ImmediateSaleListing immediateSaleListing =
        immediateListingRepository.findByImmediateSaleListingID(listingID);

    Date currentDate = DateUtil.getCurrentDate();

    if (immediateSaleListing == null) {
      String errorMessage = String.format("No listing found with provided listing id:%s", listingID);
      throw new ListingNotFoundException(errorMessage);
    }

    if (!immediateSaleListing.isActive()) {
      throw new ListingNotActiveException("Not an active listing");
    }

    ListingReviewResponse listingReviewResponse =
        ListingReviewResponse.builder()
            .listingId(listingID)
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

    immediateListingRepository.save(immediateSaleListing);

    return listingReviewResponse;
  }

  /**
   * Approve/Deny a auction sale listing and return the status of listing.
   * @param reviewRequest containing the listing id, status to be updated and sell category
   * @param adminEmail email of approver
   * @return A {@code ListingReviewResponse} object containing the id of listing and status - Approved/Denied
   */
  private ListingReviewResponse setStatusOfAuctionSaleListing(
      String adminEmail, ListingReviewRequest reviewRequest) {

    var listingID = reviewRequest.getListingId();

    AuctionSaleListing auctionSaleListing =
        auctionSaleListingRepository.findByAuctionSaleListingID(listingID);

    Date currentDate = DateUtil.getCurrentDate();

    if (auctionSaleListing == null) {
      String errorMessage = String.format("No listing found with provided listing id:%s", listingID);
      throw new ListingNotFoundException(errorMessage);
    }

    if (!auctionSaleListing.isActive()) {
      throw new ListingNotActiveException("Not an active listing");
    }

    ListingReviewResponse listingReviewResponse =
        ListingReviewResponse.builder()
            .listingId(listingID)
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

    return listingReviewResponse;
  }
}
