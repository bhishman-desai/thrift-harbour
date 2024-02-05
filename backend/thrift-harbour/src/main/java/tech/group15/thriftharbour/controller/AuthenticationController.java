package tech.group15.thriftharbour.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.group15.thriftharbour.dto.RefreshTokenRequest;
import tech.group15.thriftharbour.dto.SignInRequest;
import tech.group15.thriftharbour.dto.SignInResponse;
import tech.group15.thriftharbour.dto.SignUpRequest;
import tech.group15.thriftharbour.model.User;
import tech.group15.thriftharbour.service.AuthenticationService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
  private final AuthenticationService authenticationService;

  @PostMapping("/signup")
  public ResponseEntity<User> signUp(@RequestBody SignUpRequest signUpRequest) {
    return ResponseEntity.ok(authenticationService.signUp(signUpRequest));
  }

  @PostMapping("/signin")
  public ResponseEntity<SignInResponse> signUp(@RequestBody SignInRequest signInRequest) {
    return ResponseEntity.ok(authenticationService.signIn(signInRequest));
  }

  @PostMapping("/refresh")
  public ResponseEntity<SignInResponse> refresh(
      @RequestBody RefreshTokenRequest refreshTokenRequest) {
    return ResponseEntity.ok(authenticationService.refreshToken(refreshTokenRequest));
  }
}
