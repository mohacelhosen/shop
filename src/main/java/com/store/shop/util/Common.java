package com.store.shop.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public abstract class Common {
    public static String getTimeStamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss"));
    }

    public static String getRequestId() {
        return String.valueOf(UUID.randomUUID()).replace("-", "").substring(0, 10);
    }
}
