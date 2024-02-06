package tech.group15.thriftharbour.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import tech.group15.thriftharbour.service.JWTService;
import tech.group15.thriftharbour.service.UserService;

@Component
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {

  private final JWTService jwtService;
  private final UserService userService;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    final String authHeader = request.getHeader("Authorization");
    final String jwt;
    final String userEmail;

    /* If the Authorization header is empty or does not start with "Bearer ", continue with the next filter */
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }

    /* Extracting the JWT from the Authorization header */
    jwt = authHeader.substring(7);
    /* Extracting the username from the JWT */
    try {
      userEmail = jwtService.extractUserName(jwt);
    } catch (Exception exception) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.setContentType("application/json");
      String jsonResponse = "{ \"message\": \"User not authorized\" }";
      response.getWriter().write(jsonResponse);
      return;
    }

    /* If the username is empty and there is no current authentication, validate the JWT */
    if (!userEmail.isEmpty() && SecurityContextHolder.getContext().getAuthentication() == null) {
      UserDetails userDetails = userService.userDetailsService().loadUserByUsername(userEmail);

      /* If the JWT is valid, authenticate the user */
      if (jwtService.isTokenValid(jwt, userDetails)) {
        /* Creating an empty security context */
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        /* Creating an authentication token for the user */
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
            new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());

        /* Setting the details of the authentication token */
        usernamePasswordAuthenticationToken.setDetails(
            new WebAuthenticationDetailsSource().buildDetails(request));
        /* Setting the authentication in the security context */
        securityContext.setAuthentication(usernamePasswordAuthenticationToken);
        /* Setting the security context in the SecurityContextHolder */
        SecurityContextHolder.setContext(securityContext);
      }
    }
    /* Continue with the next filter */
    filterChain.doFilter(request, response);
  }
}
