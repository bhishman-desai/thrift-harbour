package tech.group15.thriftharbour.exception;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  /* If the email id already exists in the system */
  @ExceptionHandler(EmailAlreadyExistsException.class)
  public ResponseEntity<Object> handleEmailAlreadyExistsExceptionException(
      EmailAlreadyExistsException exception) {
    Map<String, Object> body = new HashMap<>();
    body.put("message", exception.getMessage());

    return new ResponseEntity<>(body, HttpStatus.CONFLICT);
  }
}
