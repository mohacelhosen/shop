package com.store.shop.exception;

public class DuplicateKeyException extends RuntimeException{
    public DuplicateKeyException(String message){
        super(message);
    }
}
