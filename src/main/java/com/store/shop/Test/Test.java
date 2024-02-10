package com.store.shop.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Test {
    public static void main(String[] args) {
        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss")));
        System.out.println(String.valueOf(UUID.randomUUID()).replace("-","").substring(0,10));
    }
}
