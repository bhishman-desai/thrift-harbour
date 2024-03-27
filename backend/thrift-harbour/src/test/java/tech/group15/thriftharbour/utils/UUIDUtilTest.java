package tech.group15.thriftharbour.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UUIDUtilTest {

    @Test
    void testGenerateUUID() {
        String result = UUIDUtil.generateUUID();
        Assertions.assertFalse(result.isEmpty());
    }
}
