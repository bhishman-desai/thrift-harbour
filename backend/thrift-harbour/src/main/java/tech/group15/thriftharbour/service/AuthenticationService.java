package tech.group15.thriftharbour.service;

import tech.group15.thriftharbour.dto.request.*;
import tech.group15.thriftharbour.dto.response.ForgotPassResponse;
import tech.group15.thriftharbour.dto.response.SignInResponse;
import tech.group15.thriftharbour.model.User;

public interface AuthenticationService {
  User signUp(SignUpRequest signUpRequest);
  SignInResponse signIn(SignInRequest signInRequest);

  SignInResponse refreshToken(RefreshTokenRequest refreshTokenRequest);

  ForgotPassResponse forgotPassword(ForgotPassRequest forgotPassRequest);

  Object resetPassTokenVerify(String token);

  Object resetPassword(ResetPassRequest resetPassRequest);
}
