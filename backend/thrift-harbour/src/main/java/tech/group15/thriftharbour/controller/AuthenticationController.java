package tech.group15.thriftharbour.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import tech.group15.thriftharbour.dto.*;
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

  /**
   * Initiates the password reset process for a user who has forgotten their password.
   *
   * @param forgotPassRequest The request body containing the email id to identify the user.
   * @return A {@code ResponseEntity<ForgotPassResponse>} object containing the response message of the request.
   */
  @PostMapping("/forgot-password")
  public ResponseEntity<ForgotPassResponse> forgetPassword(@RequestBody ForgotPassRequest forgotPassRequest) {
    return ResponseEntity.ok(authenticationService.forgotPassword(forgotPassRequest));
  }

  /**
   * Verifies a password reset token that has been sent to a user as part of the password reset process.
   *
   * @param token The password reset token to be verified.
   * @return A {@code RedirectView} object containing the URL to which the user will be redirected.
   */
  @GetMapping("/verify-password-reset-token/{token}")
  public RedirectView verifyPasswordResetToken(@PathVariable String token) {
    return new RedirectView((String) authenticationService.resetPassTokenVerify(token));
  }

  /**
   * Processes a request to reset a user's password.
   *
   * @param resetPassRequest The request body containing the necessary information for resetting the password.
   * @return A {@code ResponseEntity<Object>} object that contains the message of the reset password operation.
   */
  @PostMapping("/reset-password")
  public ResponseEntity<Object> resetPassword(@RequestBody ResetPassRequest resetPassRequest) {
    return ResponseEntity.ok(authenticationService.resetPassword(resetPassRequest));
  }
}
