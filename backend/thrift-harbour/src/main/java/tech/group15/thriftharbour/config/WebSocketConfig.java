package tech.group15.thriftharbour.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.DefaultContentTypeResolver;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import tech.group15.thriftharbour.constant.OriginConstant;

/** Configuration class for WebSocket setup with STOMP messaging protocol. */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

  /**
   * Configures the message broker for WebSocket communication.
   *
   * @param registry The {@code MessageBrokerRegistry} to be configured.
   */
  @Override
  public void configureMessageBroker(MessageBrokerRegistry registry) {
    registry.enableSimpleBroker(
        "/user"); /* For @SendTo annotation - the prefix for all routes defined in SendTo */
    registry.setApplicationDestinationPrefixes(
        "/app"); /* For @MessageMapping annotation - the prefix for all routes defined in MessageMapping */
    registry.setUserDestinationPrefix("/user");
  }

  /**
   * Registers STOMP endpoints for WebSocket communication.
   *
   * @param registry The {@code StompEndpointRegistry} to be configured.
   */
  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    /* Adding without sockJS for elasticity */
    registry
        .addEndpoint("/ws")
        .setAllowedOrigins(OriginConstant.allowedOrigins.toArray(new String[0]))
        .setAllowedOriginPatterns("*");

    registry
        .addEndpoint("/ws") /* Build a connection on /ws route */
        .setAllowedOrigins(OriginConstant.allowedOrigins.toArray(new String[0]))
        .setAllowedOriginPatterns("*")
        .withSockJS();
  }

  /**
   * Configures message converters for WebSocket communication.
   *
   * @param messageConverters The list of {@code MessageConverter} to be configured.
   * @return Always returns false.
   */
  @Override
  public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
    DefaultContentTypeResolver defaultContentTypeResolver = new DefaultContentTypeResolver();
    defaultContentTypeResolver.setDefaultMimeType(MimeTypeUtils.APPLICATION_JSON);
    MappingJackson2MessageConverter converter =
        new MappingJackson2MessageConverter();
    converter.setObjectMapper(new ObjectMapper());
    converter.setContentTypeResolver(defaultContentTypeResolver);

    return false;
  }
}
