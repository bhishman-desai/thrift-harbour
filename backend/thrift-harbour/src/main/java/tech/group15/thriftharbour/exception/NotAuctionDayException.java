package tech.group15.thriftharbour.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotAuctionDayException extends RuntimeException{

    public NotAuctionDayException(String errorMessage){super(errorMessage);}
}
