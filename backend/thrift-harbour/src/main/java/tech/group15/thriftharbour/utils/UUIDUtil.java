package tech.group15.thriftharbour.utils;

import java.util.UUID;

public class UUIDUtil {

    public static String generateUUID(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
