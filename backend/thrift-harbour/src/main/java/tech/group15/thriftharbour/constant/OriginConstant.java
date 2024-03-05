package tech.group15.thriftharbour.constant;

import java.util.List;

public class OriginConstant {
  public static final List<String> allowedOrigins =
      List.of("http://localhost:3000", "http://172.17.1.50:3000");

  private OriginConstant() {}
}
