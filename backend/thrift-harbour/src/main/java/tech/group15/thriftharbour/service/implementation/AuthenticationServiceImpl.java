package tech.group15.thriftharbour.service.implementation;

import java.util.Date;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tech.group15.thriftharbour.dto.request.*;
import tech.group15.thriftharbour.dto.response.ForgotPassResponse;
import tech.group15.thriftharbour.dto.response.SignInResponse;
import tech.group15.thriftharbour.exception.EmailAlreadyExistsException;
import tech.group15.thriftharbour.mapper.UserMapper;
import tech.group15.thriftharbour.model.PasswordResetToken;
import tech.group15.thriftharbour.model.User;
import tech.group15.thriftharbour.repository.PasswordResetTokenRepository;
import tech.group15.thriftharbour.repository.UserRepository;
import tech.group15.thriftharbour.service.AuthenticationService;
import tech.group15.thriftharbour.service.EmailService;
import tech.group15.thriftharbour.service.JWTService;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;
  private final JWTService jwtService;
  private final PasswordResetTokenRepository passwordResetTokenRepository;
  private final EmailService emailService;

  public User signUp(SignUpRequest signUpRequest) {
    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      throw new EmailAlreadyExistsException("Email already exists!");
    }
    User user = UserMapper.convertSignUpRequestToUserModel(signUpRequest, passwordEncoder);
    return userRepository.save(user);
  }

  public SignInResponse signIn(SignInRequest signInRequest) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            signInRequest.getEmail(), signInRequest.getPassword()));
    var user =
        userRepository
            .findByEmail(signInRequest.getEmail())
            .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
    var token = jwtService.generateToken(user);
    var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

    return UserMapper.generateSignInResponse(token, refreshToken);
  }

  public SignInResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
    String userEmail = jwtService.extractUserName(refreshTokenRequest.getToken());
    User user =
        userRepository
            .findByEmail(userEmail)
            .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

    if (jwtService.isTokenValid(refreshTokenRequest.getToken(), user)) {
      var token = jwtService.generateToken(user);
      return UserMapper.generateSignInResponse(token, refreshTokenRequest.getToken());
    }

    return null;
  }

  /**
   * This method will verify user's email then it will send reset password link to registered email id.
   *
   * @param forgotPassRequest The request includes the user's email id.
   * @return A {@code ForgotPassResponse} object containing the response message.
   */
  public ForgotPassResponse forgotPassword(ForgotPassRequest forgotPassRequest) {
    User user =
        userRepository
            .findByEmail(forgotPassRequest.getEmail())
            .orElseThrow(() -> new IllegalArgumentException("Invalid email"));

    Optional<PasswordResetToken> oldPassResetToken = passwordResetTokenRepository.findByUser(user);
    oldPassResetToken.ifPresent(
        passwordResetToken ->
            passwordResetTokenRepository.deleteAllByTokenID(passwordResetToken.getTokenID()));
    PasswordResetToken token = new PasswordResetToken();
    token.setToken(UUID.randomUUID().toString());
    token.setUser(user);
    token.setExpiryDate(new Date(System.currentTimeMillis() + 1000 * 60 * 30));
    passwordResetTokenRepository.save(token);

    emailService.sendEmail(
        forgotPassRequest.getEmail(),
        "Thrift Harbour Reset Password Link",
        "http://172.17.1.50:8080/api/v1/auth/verify-password-reset-token/" + token.getToken());

    return UserMapper.generateForgotPassResponse("Email Sent Successfully");
  }

  /**
   * Verifies the expiry of a password reset token.
   *
   * @param token The password reset token to be verified.
   * @return The url of reset password page if token is valid otherwise url of login page.
   */
  public Object resetPassTokenVerify(String token) {
    PasswordResetToken passResetToken =
        passwordResetTokenRepository
            .findByToken(token)
            .orElseThrow(() -> new IllegalArgumentException("Invalid token"));

    if (!passResetToken.getExpiryDate().before(new Date())) {
      return "http://172.17.1.50:3000/reset-password" + "?token=" + token;
    }

    passwordResetTokenRepository.deleteAllByTokenID(passResetToken.getTokenID());
    return "http://172.17.1.50:3000/login" + "?msg=Link Expired";
  }

  /**
   * The method will reset password of the user.
   *
   * @param resetPassRequest A {@code ResetPassRequest} object containing all necessary information to reset the password.
   * @return A {@code Object} object containing the response message.
   */
  public Object resetPassword(ResetPassRequest resetPassRequest) {
    PasswordResetToken passResetToken =
        passwordResetTokenRepository
            .findByToken(resetPassRequest.getToken())
            .orElseThrow(() -> new IllegalArgumentException("Invalid token"));
    User user =
        userRepository
            .findById(passResetToken.getUser().getUserID())
            .orElseThrow(() -> new IllegalArgumentException("User Not Found"));

    passwordResetTokenRepository.deleteAllByTokenID(passResetToken.getTokenID());
    user.setPassword(passwordEncoder.encode((resetPassRequest.getPassword())));
    userRepository.save(user);
    return "Password Changed Successfully";
  }
}
