package tech.group15.thriftharbour.service;

import tech.group15.thriftharbour.dto.*;
import tech.group15.thriftharbour.model.User;

public interface AuthenticationService {
  User signUp(SignUpRequest signUpRequest);
  SignInResponse signIn(SignInRequest signInRequest);

  SignInResponse refreshToken(RefreshTokenRequest refreshTokenRequest);

  ForgotPassResponse forgotPassword(ForgotPassRequest forgotPassRequest);

  Object resetPassTokenVerify(String token);

  Object resetPassword(ResetPassRequest resetPassRequest);
}
