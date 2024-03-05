package tech.group15.thriftharbour.constant;

import java.util.List;

/** {@code OriginConstant} class holds a list of allowed origins for CORS configuration. */
public class OriginConstant {
  /** List of allowed origins for CORS configuration. */
  public static final List<String> allowedOrigins =
      List.of("http://localhost:3000", "http://172.17.1.50:3000");

  /** Private constructor to prevent instantiation of the utility class. */
  private OriginConstant() {}
}
