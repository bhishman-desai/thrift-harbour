package tech.group15.thriftharbour.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
    info =
        @Info(
            description = "OpenAPI documentation for Thrift Harbour",
            title = "OpenAPI Specification - Thrift Harbour"),
    servers = {@Server(description = "Local", url = "http://localhost:8080")},
    security = {@SecurityRequirement(name = "bearerAuth")})
@SecurityScheme(
    name = "bearerAuth",
    description = "JWT Auth",
    scheme = "bearer",
    type = SecuritySchemeType.HTTP,
    bearerFormat = "JWT",
    in = SecuritySchemeIn.HEADER)
@Configuration
public class OpenAPIConfiguration {}
