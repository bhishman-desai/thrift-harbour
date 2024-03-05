package tech.group15.thriftharbour.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tech.group15.thriftharbour.dto.BuyerRatingsRequest;
import tech.group15.thriftharbour.dto.SellerRatingsRequest;
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

    BuyerRatings buyerRatings =
        RatingsMapper.convertBuyerRatingsRequestToBuyerRatingsModel(
            buyerRatingsRequest, user.getUserID());

    buyerRatingsRepository.save(buyerRatings);

    return "Ratings Added Successfully";
  }

  @Override
  public String addSellerRatings(
      String authorizationHeader, SellerRatingsRequest sellerRatingsRequest) {

    String userName = jwtService.extractUserNameFromRequestHeaders(authorizationHeader);

    User user =
        userRepository
            .findByEmail(userName)
            .orElseThrow(() -> new UsernameNotFoundException("User not found!"));

    SellerRatings sellerRatings =
        RatingsMapper.convertSellerRatingsRequestToSellerRatingsModel(
            sellerRatingsRequest, user.getUserID());

    sellerRatingsRepository.save(sellerRatings);

    return "Ratings Added Successfully";
  }
}
