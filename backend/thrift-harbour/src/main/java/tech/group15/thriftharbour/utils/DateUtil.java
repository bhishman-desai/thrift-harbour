package tech.group15.thriftharbour.utils;

import tech.group15.thriftharbour.exception.InvalidAuctionDateException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    private DateUtil(){}
    public static Date getCurrentDate(){
        return new Date();
    }

    public static Date getDateFromString(String date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return simpleDateFormat.parse(date);
        } catch (ParseException e) {
            throw new InvalidAuctionDateException("Please select a valid Date");
        }
    }
}
