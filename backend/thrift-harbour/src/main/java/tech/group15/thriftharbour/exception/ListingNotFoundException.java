package tech.group15.thriftharbour.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ListingNotFoundException extends RuntimeException{
    public ListingNotFoundException(String errorMessage){
        super(errorMessage);
    }
}
