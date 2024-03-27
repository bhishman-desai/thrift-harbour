package tech.group15.thriftharbour.mapper;

import java.util.ArrayList;
import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;
import tech.group15.thriftharbour.dto.response.ForgotPassResponse;
import tech.group15.thriftharbour.dto.response.SellerResponse;
import tech.group15.thriftharbour.dto.response.SignInResponse;
import tech.group15.thriftharbour.dto.request.SignUpRequest;
import tech.group15.thriftharbour.enums.RoleEnum;
import tech.group15.thriftharbour.model.User;

public class UserMapper {

  private UserMapper() {}

  public static User convertSignUpRequestToUserModel(
      SignUpRequest signUpRequest, PasswordEncoder passwordEncoder) {
    User user = new User();

    user.setFirstName(signUpRequest.getFirstName());
    user.setLastName(signUpRequest.getLastName());
    user.setEmail(signUpRequest.getEmail());
    user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
    user.setRole(RoleEnum.USER);

    return user;
  }

  public static SignInResponse generateSignInResponse(String token, String refreshToken, Integer userId) {
    SignInResponse signInResponse = new SignInResponse();
    signInResponse.setUserID(userId);
    signInResponse.setToken(token);
    signInResponse.setRefreshToken(refreshToken);

    return signInResponse;
  }

  public static ForgotPassResponse generateForgotPassResponse(String msg) {
    ForgotPassResponse forgotPassResponse = new ForgotPassResponse();
    forgotPassResponse.setMsg(msg);

    return forgotPassResponse;
  }

  public static List<SellerResponse> generateSellerInformationResponse(List<User> users) {
    List<SellerResponse> sellerResponses = new ArrayList<>();

    for (User user : users) {
      /* Making sure there is only one seller added even though they might have multiple listing in the table */
      if (!containsUserID(sellerResponses, user.getUserID())) {
        SellerResponse sellerResponse = new SellerResponse();

        sellerResponse.setUserID(user.getUserID());
        sellerResponse.setFirstName(user.getFirstName());
        sellerResponse.setLastName(user.getLastName());
        sellerResponse.setEmail(user.getEmail());

        sellerResponses.add(sellerResponse);
      }
    }

    return sellerResponses;
  }

  /* Helper methods */
  private static boolean containsUserID(List<SellerResponse> sellerResponses, int userID) {
    for (SellerResponse sellerResponse : sellerResponses) {
      if (sellerResponse.getUserID() == userID) {
        return true;
      }
    }
    return false;
  }
}
