package tech.group15.thriftharbour.service.implementation;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import tech.group15.thriftharbour.service.JWTService;

@Service
public class JWTServiceImpl implements JWTService {
  /* Methods that generate JWT token, and extract information from the token */

  /**
   * This method generates a JWT (JSON Web Token) for a given user.
   *
   * @param userDetails The user details object from which to extract the username.
   * @return String - A JWT token string.
   */
  public String generateToken(UserDetails userDetails) {
    return Jwts.builder()
        .setSubject(userDetails.getUsername())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
        .signWith(getSignInKey(), SignatureAlgorithm.HS256)
        .compact();
  }

  /**
   * This method generates a refresh JWT (JSON Web Token) for a given user.
   *
   * @param extraClaims A HashMap of additional claims to be included in the JWT.
   * @param userDetails The user details object from which to extract the username.
   * @return String - A JWT refresh token string.
   */
  public String generateRefreshToken(HashMap<String, Object> extraClaims, UserDetails userDetails) {
    return Jwts.builder()
        .setClaims(extraClaims)
        .setSubject(userDetails.getUsername())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + 604800000)) /* 7 days*/
        .signWith(getSignInKey(), SignatureAlgorithm.HS256)
        .compact();
  }

  /**
   * This method generates a signing key for authentication.
   *
   * @return Key - The signing key for HMAC SHA (Hash-based Message Authentication Code, Secure Hash
   *     Algorithm).
   */
  private Key getSignInKey() {
    byte[] key =
        Decoders.BASE64.decode("9a4f2c8d3b7a1e6f45c8a0b3f267d8b1d4e6f3c8a9d2b5f8e3a9c8b5f6v8a3d9");
    return Keys.hmacShaKeyFor(key);
  }

  /**
   * This method extracts a specific claim from a token.
   *
   * @param <T> The type of the claim value.
   * @param token The token from which to extract the claim.
   * @param claimsResolvers A function that takes a Claims object and returns a claim value of type
   *     T.
   * @return The claim value of type T extracted from the token.
   */
  private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
    final Claims claims = extractAllClaims(token);
    /* Return a particular claim */
    return claimsResolvers.apply(claims);
  }

  /**
   * This method extracts all claims from a given token.
   *
   * @param token The token from which to extract the claims.
   * @return The Claims object containing all claims present in the token.
   */
  private Claims extractAllClaims(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(getSignInKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  /**
   * This method extracts the username from a token.
   *
   * @param token The JWT from which to extract the username.
   * @return String - The username extracted from the JWT.
   */
  public String extractUserName(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public String extractUserNameFromRequestHeaders(String authorizationHeader){
    String token = authorizationHeader.substring("Bearer ".length());
    return extractUserName(token);
  }

  /**
   * This method checks if a JWT (JSON Web Token) is valid.
   *
   * @param token The JWT to validate.
   * @param userDetails The user details object to compare with the username in the JWT.
   * @return boolean - Returns true if the username in the JWT matches the username in the user
   *     details and the JWT is not expired, false otherwise.
   */
  public boolean isTokenValid(String token, UserDetails userDetails) {
    final String username = extractUserName(token);
    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }

  /**
   * This method checks if a JWT (JSON Web Token) is expired.
   *
   * @param token The JWT to check for expiration.
   * @return boolean - Returns true if the JWT is expired, false otherwise.
   */
  private boolean isTokenExpired(String token) {
    return extractClaim(token, Claims::getExpiration).before(new Date());
  }
}
