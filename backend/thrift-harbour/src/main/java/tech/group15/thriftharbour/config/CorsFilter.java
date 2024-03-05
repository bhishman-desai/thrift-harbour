package tech.group15.thriftharbour.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import tech.group15.thriftharbour.constant.OriginConstant;

/**
 * Filter class for handling Cross-Origin Resource Sharing (CORS) in web applications. This filter
 * allows or restricts cross-origin requests based on configured origins.
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements Filter {
  /**
   * Performs CORS filtering by setting appropriate headers in the HTTP response.
   *
   * @param request The ServletRequest object.
   * @param response The ServletResponse object.
   * @param chain The FilterChain object for passing the request and response to the next filter.
   * @throws IOException If an I/O error occurs during the filter execution.
   * @throws ServletException If any servlet-related error occurs during the filter execution.
   */
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    HttpServletResponse httpServletResponse = (HttpServletResponse) response;
    HttpServletRequest httpServletRequest = (HttpServletRequest) request;

    String origin = httpServletRequest.getHeader("Origin");

    /* Set the 'Access-Control-Allow-Origin' header based on the configured allowed origins */
    if (origin != null) {
      /* Check if the origin is in the allowed origins list, otherwise use a wildcard */
      httpServletResponse.setHeader(
          "Access-Control-Allow-Origin",
          OriginConstant.allowedOrigins.contains(origin) ? origin : "*");
    } else {
      httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
    }
    httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
    httpServletResponse.setHeader("Vary", "Origin");
    httpServletResponse.setHeader(
        "Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS");
    httpServletResponse.setHeader("Access-Control-Allow-Headers", "*");

    /* Just REPLY OK if request method is OPTIONS for CORS (pre-flight) */
    if (httpServletRequest.getMethod().equals("OPTIONS")) {
      httpServletResponse.setHeader("Access-Control-Max-Age", "86400");
      httpServletResponse.setStatus(HttpServletResponse.SC_OK);
      return;
    }

    chain.doFilter(request, response);
  }
}
