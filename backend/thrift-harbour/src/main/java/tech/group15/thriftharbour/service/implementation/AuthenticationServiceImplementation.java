package tech.group15.thriftharbour.service.implementation;

import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tech.group15.thriftharbour.dto.RefreshTokenRequest;
import tech.group15.thriftharbour.dto.SignInRequest;
import tech.group15.thriftharbour.dto.SignInResponse;
import tech.group15.thriftharbour.dto.SignUpRequest;
import tech.group15.thriftharbour.exception.EmailAlreadyExistsException;
import tech.group15.thriftharbour.mapper.UserMapper;
import tech.group15.thriftharbour.model.User;
import tech.group15.thriftharbour.repository.UserRepository;
import tech.group15.thriftharbour.service.AuthenticationService;
import tech.group15.thriftharbour.service.JWTService;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImplementation implements AuthenticationService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;
  private final JWTService jwtService;

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
}
