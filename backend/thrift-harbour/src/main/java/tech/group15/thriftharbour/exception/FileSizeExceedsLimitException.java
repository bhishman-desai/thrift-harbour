package tech.group15.thriftharbour.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class FileSizeExceedsLimitException extends RuntimeException{

    public FileSizeExceedsLimitException(String errorMessage){super(errorMessage);}
}
