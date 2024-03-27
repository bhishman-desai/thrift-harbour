package tech.group15.thriftharbour.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tech.group15.thriftharbour.exception.InvalidAuctionDateException;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

class DateUtilTest {

    @Test
    void testGetCurrentDate() {
        Date result = DateUtil.getCurrentDate();
        Assertions.assertEquals(new Date(), result);
    }

    @Test
    void testGetDateFromString() {
        Date result = DateUtil.getDateFromString("2024-3-26");
        Assertions.assertEquals(new GregorianCalendar(2024, Calendar.MARCH, 26, 0, 0).getTime(), result);
    }

    @Test
    void testGetDateFromIncorrectString(){
        Assertions.assertThrows(InvalidAuctionDateException.class,
                () -> DateUtil.getDateFromString(""));
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme