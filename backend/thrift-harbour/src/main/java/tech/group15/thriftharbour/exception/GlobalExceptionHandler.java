package tech.group15.thriftharbour.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import tech.group15.thriftharbour.utils.HttpUtils;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  /* Handle any exception which occurs in the system */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> handleAnyException(Exception exception) {
    return HttpUtils.generateMessageResponse(exception);
  }
}
