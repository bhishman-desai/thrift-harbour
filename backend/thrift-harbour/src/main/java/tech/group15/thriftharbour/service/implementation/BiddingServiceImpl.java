package tech.group15.thriftharbour.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tech.group15.thriftharbour.constant.ErrorConstant;
import tech.group15.thriftharbour.constant.InfoConstant;
import tech.group15.thriftharbour.dto.request.PlaceBidRequest;
import tech.group15.thriftharbour.exception.ListingNotFoundException;
import tech.group15.thriftharbour.exception.LowBidException;
import tech.group15.thriftharbour.model.AuctionSaleListing;
import tech.group15.thriftharbour.model.Bidding;
import tech.group15.thriftharbour.model.User;
import tech.group15.thriftharbour.repository.AuctionSaleListingRepository;
import tech.group15.thriftharbour.repository.BiddingRepository;
import tech.group15.thriftharbour.repository.UserRepository;
import tech.group15.thriftharbour.service.BiddingService;
import tech.group15.thriftharbour.service.JWTService;

@Service
@RequiredArgsConstructor
public class BiddingServiceImpl implements BiddingService {

  private final JWTService jwtService;

  private final AuctionSaleListingRepository auctionSaleListingRepository;

  private final BiddingRepository biddingRepository;

  private final UserRepository userRepository;

  /**
   * Retrieves all immediate sale listings with its seller details.
   *
   * @param authorizationHeader The authorization header containing the JWT of user.
   * @param placeBidRequest Containing the Listing ID and the bid to be placed.
   * @return Message to user - Bid is placed successfully.
   */
  @Override
  public String placeBid(String authorizationHeader, PlaceBidRequest placeBidRequest) {

    String userEmail = jwtService.extractUserNameFromRequestHeaders(authorizationHeader);

    User seller = findUserByMail(userEmail);

    String listingID = placeBidRequest.getAuctionSaleListingID();
    AuctionSaleListing listing = auctionSaleListingRepository.findByAuctionSaleListingID(listingID);

    if(listing == null){
      throw new ListingNotFoundException(ErrorConstant.PRODUCT_NOT_FOUND);
    }

    if(placeBidRequest.getBidAmount() <= listing.getHighestBid()){
      throw new LowBidException(ErrorConstant.LOW_BID_EXCEPTION);
    }

    Bidding userPlacedBid =
        Bidding.builder()
            .auctionSaleListingID(listing.getAuctionSaleListingID())
            .userEmail(userEmail)
            .placedBid(placeBidRequest.getBidAmount())
            .build();

    listing.setHighestBid(placeBidRequest.getBidAmount());
    listing.setCurrentHighestBidUserMail(seller.getEmail());

    auctionSaleListingRepository.save(listing);
    biddingRepository.save(userPlacedBid);

    return InfoConstant.BID_PLACED_SUCCESFULLY;
  }

  // Find the user details for provided mail id or throw user not found exception
  private User findUserByMail(String mail) {
    return userRepository
            .findByEmail(mail)
            .orElseThrow(() -> new UsernameNotFoundException(ErrorConstant.USER_NOT_FOUND));
  }
}
