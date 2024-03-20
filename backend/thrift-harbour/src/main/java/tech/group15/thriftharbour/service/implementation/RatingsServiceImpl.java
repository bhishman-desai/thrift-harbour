package tech.group15.thriftharbour.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tech.group15.thriftharbour.dto.request.BuyerRatingsRequest;
import tech.group15.thriftharbour.dto.request.SellerRatingsRequest;
import tech.group15.thriftharbour.mapper.RatingsMapper;
import tech.group15.thriftharbour.model.BuyerRatings;
import tech.group15.thriftharbour.model.SellerRatings;
import tech.group15.thriftharbour.model.User;
import tech.group15.thriftharbour.repository.BuyerRatingsRepository;
import tech.group15.thriftharbour.repository.SellerRatingsRepository;
import tech.group15.thriftharbour.repository.UserRepository;
import tech.group15.thriftharbour.service.JWTService;
import tech.group15.thriftharbour.service.RatingsService;

@Service
@RequiredArgsConstructor
public class RatingsServiceImpl implements RatingsService {

  private final JWTService jwtService;

  private final UserRepository userRepository;

  private final BuyerRatingsRepository buyerRatingsRepository;

  private final SellerRatingsRepository sellerRatingsRepository;

  @Override
  public String addBuyerRatings(
      String authorizationHeader, BuyerRatingsRequest buyerRatingsRequest) {

    String userName = jwtService.extractUserNameFromRequestHeaders(authorizationHeader);

    User user =
        userRepository
            .findByEmail(userName)
            .orElseThrow(() -> new UsernameNotFoundException("User not found!"));

    var userId = user.getUserID();
    BuyerRatings buyerRatings =
        RatingsMapper.convertBuyerRatingsRequestToBuyerRatingsModel(
            buyerRatingsRequest, userId);

    buyerRatingsRepository.save(buyerRatings);

    var ratingToUserId = buyerRatings.getRatingToUserId();
    userRepository.updateBuyerRatings(
        buyerRatingsRepository.findAvgBuyerRatings(ratingToUserId), ratingToUserId);

    return "Buyer Ratings Added Successfully";
  }

  @Override
  public String addSellerRatings(
      String authorizationHeader, SellerRatingsRequest sellerRatingsRequest) {

    String userName = jwtService.extractUserNameFromRequestHeaders(authorizationHeader);

    User user =
        userRepository
            .findByEmail(userName)
            .orElseThrow(() -> new UsernameNotFoundException("User not found!"));

    var userId = user.getUserID();
    SellerRatings sellerRatings =
        RatingsMapper.convertSellerRatingsRequestToSellerRatingsModel(
            sellerRatingsRequest, userId);

    sellerRatingsRepository.save(sellerRatings);

    var ratingToUserId= sellerRatings.getRatingToUserId();

    userRepository.updateSellerRatings(
            sellerRatingsRepository.findAvgSellerRatings(ratingToUserId), ratingToUserId);

    return "Seller Ratings Added Successfully";
  }
}
