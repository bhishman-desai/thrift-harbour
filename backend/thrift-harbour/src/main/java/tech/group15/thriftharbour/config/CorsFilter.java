package tech.group15.thriftharbour.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import tech.group15.thriftharbour.constant.OriginConstant;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements Filter {
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpServletResponse httpServletResponse = (HttpServletResponse) response;
    HttpServletRequest httpServletRequest = (HttpServletRequest) request;

    String origin = httpServletRequest.getHeader("Origin");
    httpServletResponse.setHeader(
        "Access-Control-Allow-Origin",
        OriginConstant.allowedOrigins.contains(origin) ? origin : "*");
    httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
    httpServletResponse.setHeader("Vary", "Origin");
    httpServletResponse.setHeader(
        "Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS");
    httpServletResponse.setHeader("Access-Control-Allow-Headers", "*");

    /* Just REPLY OK if request method is OPTIONS for CORS (pre-flight)*/
    if (httpServletRequest.getMethod().equals("OPTIONS")) {
      httpServletResponse.setHeader("Access-Control-Max-Age", "86400");
      httpServletResponse.setStatus(HttpServletResponse.SC_OK);
      return;
    }

    chain.doFilter(request, response);
  }
}
