package com.ecom.exception;

public class InvalidArgumentException extends RuntimeException{

    InvalidArgumentException(String message){
        super(message);
    }
}
