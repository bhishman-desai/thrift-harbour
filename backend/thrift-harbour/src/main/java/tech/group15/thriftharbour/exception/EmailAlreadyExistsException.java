package tech.group15.thriftharbour.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class EmailAlreadyExistsException extends RuntimeException {
  public EmailAlreadyExistsException(String errorMessage) {
    super(errorMessage);
  }
}
