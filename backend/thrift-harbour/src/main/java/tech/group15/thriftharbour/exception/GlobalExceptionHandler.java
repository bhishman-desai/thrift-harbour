package tech.group15.thriftharbour.exception;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  /* Handle any exception which occurs in the system */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> handleAnyException(Exception exception) {
    return this.generateMessageResponse(exception);
  }

  private ResponseEntity<Object> generateMessageResponse(Exception exception) {

    /* Get the status code */
    HttpStatus httpStatus = determineHttpStatus(exception);

    /* Generate the response with message */
    Map<String, Object> body = new HashMap<>();
    body.put("message", exception.getMessage());

    return new ResponseEntity<>(body, httpStatus);
  }

  private HttpStatus determineHttpStatus(Exception exception) {
    /* Handle JWT related exceptions or Bad credential */
    if (exception instanceof ExpiredJwtException || exception instanceof BadCredentialsException) {
      return HttpStatus.UNAUTHORIZED;
    }
    /* If the email id already exists in the system */
    if (exception instanceof EmailAlreadyExistsException) {
      return HttpStatus.CONFLICT;
    }
    if (exception instanceof MethodArgumentNotValidException) {
      return HttpStatus.BAD_REQUEST;
    }
    /* If the listing is not found in the system */
    if (exception instanceof ListingNotFoundException) {
      return HttpStatus.BAD_REQUEST;
    }

    return HttpStatus.INTERNAL_SERVER_ERROR;
  }
}
