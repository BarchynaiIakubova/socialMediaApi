package com.barchynai.socialMediaApi.exceptions;

public class WrongPasswordException extends RuntimeException{

    public WrongPasswordException(String message) {
        super(message);
    }
}
