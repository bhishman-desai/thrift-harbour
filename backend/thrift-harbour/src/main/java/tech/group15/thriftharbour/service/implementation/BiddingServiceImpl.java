package tech.group15.thriftharbour.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tech.group15.thriftharbour.constant.ErrorConstant;
import tech.group15.thriftharbour.constant.InfoConstant;
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

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BiddingServiceImpl implements BiddingService {

  private final JWTService jwtService;

  private final AuctionSaleListingRepository auctionSaleListingRepository;

  private final BiddingRepository biddingRepository;

  private final UserRepository userRepository;

  @Override
  public String placeBid(String authorizationHeader, String auctionSaleListingID, Double bidAmount) {

    String userEmail = jwtService.extractUserNameFromRequestHeaders(authorizationHeader);

    User seller =
        userRepository
            .findByEmail(userEmail)
            .orElseThrow(() -> new UsernameNotFoundException(ErrorConstant.USER_NOT_FOUND));

    AuctionSaleListing auctionSaleListing = auctionSaleListingRepository.findByAuctionSaleListingID(auctionSaleListingID);

    if(auctionSaleListing == null){
      throw new ListingNotFoundException(ErrorConstant.PRODUCT_NOT_FOUND);
    }

    if(bidAmount <= auctionSaleListing.getHighestBid()){
      throw new LowBidException(ErrorConstant.LOW_BID_EXCEPTION);
    }

    Bidding userPlacedBid = Bidding.builder()
            .auctionSaleListingID(auctionSaleListing.getAuctionSaleListingID())
            .userEmail(userEmail)
            .placedBid(bidAmount)
            .build();

    auctionSaleListing.setHighestBid(bidAmount);
    auctionSaleListing.setCurrentHighestBidUserMail(seller.getEmail());

    auctionSaleListingRepository.save(auctionSaleListing);
    biddingRepository.save(userPlacedBid);

    return InfoConstant.BID_PLACED_SUCCESFULLY;
  }
}
