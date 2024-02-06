package tech.group15.thriftharbour.service;

import tech.group15.thriftharbour.dto.RefreshTokenRequest;
import tech.group15.thriftharbour.dto.SignInRequest;
import tech.group15.thriftharbour.dto.SignInResponse;
import tech.group15.thriftharbour.dto.SignUpRequest;
import tech.group15.thriftharbour.model.User;

public interface AuthenticationService {
  User signUp(SignUpRequest signUpRequest);
  SignInResponse signIn(SignInRequest signInRequest);

  SignInResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
