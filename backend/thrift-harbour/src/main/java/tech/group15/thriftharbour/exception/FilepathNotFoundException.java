package tech.group15.thriftharbour.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class FilepathNotFoundException extends RuntimeException{
    public FilepathNotFoundException(String errorMessage){super(errorMessage);}
}
