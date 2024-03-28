package tech.group15.thriftharbour.service.implementation;

import static tech.group15.thriftharbour.constant.JWTTokenConstant.*;

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

    String signInRequestEmail = signInRequest.getEmail();
    String signInRequestPassword = signInRequest.getPassword();

    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(signInRequestEmail, signInRequestPassword));

    User user = findUserByEmail(signInRequestEmail);
    var token = jwtService.generateToken(user);
    var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

    return UserMapper.generateSignInResponse(token, refreshToken, user.getUserID());
  }

  public SignInResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
    String userEmail = jwtService.extractUserName(refreshTokenRequest.getToken());
    User user = findUserByEmail(userEmail);

    String newToken = jwtService.onRefreshToken(refreshTokenRequest, user);
    String oldToken = refreshTokenRequest.getToken();

    return UserMapper.generateSignInResponse(newToken, oldToken, user.getUserID());
  }

  /**
   * This method will verify user's email then it will send reset password link to registered email
   * id.
   *
   * @param forgotPassRequest The request includes the user's email id.
   * @return A {@code ForgotPassResponse} object containing the response message.
   */
  public ForgotPassResponse forgotPassword(ForgotPassRequest forgotPassRequest) {
    String forgotPassRequestEmail = forgotPassRequest.getEmail();
    User user = findUserByEmail(forgotPassRequestEmail);

    Optional<PasswordResetToken> oldPassResetToken = passwordResetTokenRepository.findByUser(user);
    oldPassResetToken.ifPresent(
        passwordResetToken ->
            passwordResetTokenRepository.deleteAllByTokenID(passwordResetToken.getTokenID()));
    PasswordResetToken token = new PasswordResetToken();
    token.setToken(UUID.randomUUID().toString());
    token.setUser(user);

    Date expiryDate =
        new Date(
            System.currentTimeMillis()
                + MILLISECONDS_IN_SECOND * SECONDS_IN_MINUTE * MINUTES_IN_HOUR);
    token.setExpiryDate(expiryDate);
    passwordResetTokenRepository.save(token);

    String mailSubject = "Thrift Harbour Reset Password Link";
    String URL = "http://172.17.1.50:8080/api/v1/auth/verify-password-reset-token/";

    emailService.sendEmail(forgotPassRequestEmail, mailSubject, URL + token.getToken());

    return UserMapper.generateForgotPassResponse("Email Sent Successfully");
  }

  /**
   * Verifies the expiry of a password reset token.
   *
   * @param token The password reset token to be verified.
   * @return The url of reset password page if token is valid otherwise url of login page.
   */
  public Object resetPassTokenVerify(String token) {
    PasswordResetToken passResetToken = getPasswordResetToken(token);

    if (!passResetToken.getExpiryDate().before(new Date())) {
      return "http://172.17.1.50:3000/reset-password" + "?token=" + token;
    }

    passwordResetTokenRepository.deleteAllByTokenID(passResetToken.getTokenID());
    return "http://172.17.1.50:3000/login" + "?msg=Link Expired";
  }

  /**
   * The method will reset the password of the user.
   *
   * @param resetPassRequest A {@code ResetPassRequest} object containing all necessary information
   *     to reset the password.
   * @return A {@code Object} object containing the response message.
   */
  public Object resetPassword(ResetPassRequest resetPassRequest) {
    PasswordResetToken passResetToken = getPasswordResetToken(resetPassRequest.getToken());

    int id = passResetToken.getUser().getUserID();
    User user = findUserById(id);

    passwordResetTokenRepository.deleteAllByTokenID(passResetToken.getTokenID());
    user.setPassword(passwordEncoder.encode((resetPassRequest.getPassword())));
    userRepository.save(user);
    return "Password Changed Successfully";
  }

  /* Helper methods */
  private User findUserByEmail(String email) {
    return userRepository
        .findByEmail(email)
        .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
  }

  private User findUserById(int id) {
    return userRepository
        .findById(id)
        .orElseThrow(() -> new IllegalArgumentException("User Not Found"));
  }

  private PasswordResetToken getPasswordResetToken(String token) {
    return passwordResetTokenRepository
        .findByToken(token)
        .orElseThrow(() -> new IllegalArgumentException("Invalid token"));
  }
}
