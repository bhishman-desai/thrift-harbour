package tech.group15.thriftharbour.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import tech.group15.thriftharbour.dto.request.*;
import tech.group15.thriftharbour.dto.response.ForgotPassResponse;
import tech.group15.thriftharbour.dto.response.SignInResponse;
import tech.group15.thriftharbour.model.User;
import tech.group15.thriftharbour.service.AuthenticationService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthenticationController {
  private final AuthenticationService authenticationService;

  @PostMapping("/signup")
  public ResponseEntity<User> signUp(@RequestBody SignUpRequest signUpRequest) {
    return ResponseEntity.ok(authenticationService.signUp(signUpRequest));
  }

  @PostMapping("/signin")
  public ResponseEntity<SignInResponse> signIn(@RequestBody SignInRequest signInRequest) {
    return ResponseEntity.ok(authenticationService.signIn(signInRequest));
  }

  @PostMapping("/refresh")
  public ResponseEntity<SignInResponse> refresh(
      @RequestBody RefreshTokenRequest refreshTokenRequest) {
    return ResponseEntity.ok(authenticationService.refreshToken(refreshTokenRequest));
  }

  @PostMapping("/forgot-password")
  public ResponseEntity<ForgotPassResponse> forgetPassword(@RequestBody ForgotPassRequest forgotPassRequest) {
    return ResponseEntity.ok(authenticationService.forgotPassword(forgotPassRequest));
  }

  @GetMapping("/verify-password-reset-token/{token}")
  public RedirectView verifyPasswordResetToken(@PathVariable String token) {
    return new RedirectView((String) authenticationService.resetPassTokenVerify(token));
  }

  @PostMapping("/reset-password")
  public ResponseEntity<Object> resetPassword(@RequestBody ResetPassRequest resetPassRequest) {
    return ResponseEntity.ok(authenticationService.resetPassword(resetPassRequest));
  }
}
