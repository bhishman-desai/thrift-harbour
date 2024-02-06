package tech.group15.thriftharbour.utils;

import io.jsonwebtoken.ExpiredJwtException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import tech.group15.thriftharbour.exception.EmailAlreadyExistsException;

public class HttpUtils {
  private HttpUtils() {}

  public static ResponseEntity<Object> generateMessageResponse(Exception exception) {

    /* Get the status code */
    HttpStatus httpStatus = HttpUtils.determineHttpStatus(exception);

    /* Generate the response with message */
    Map<String, Object> body = new HashMap<>();
    body.put("message", exception.getMessage());

    return new ResponseEntity<>(body, httpStatus);
  }

  private static HttpStatus determineHttpStatus(Exception exception) {
    /* Handle JWT related exceptions */
    if (exception instanceof ExpiredJwtException) {
      return HttpStatus.UNAUTHORIZED;
    }
    /* If the email id already exists in the system */
    if (exception instanceof EmailAlreadyExistsException) {
      return HttpStatus.CONFLICT;
    }
    return HttpStatus.INTERNAL_SERVER_ERROR;
  }
}
