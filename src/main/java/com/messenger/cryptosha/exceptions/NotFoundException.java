package com.messenger.cryptosha.exceptions;

public class NotFoundException extends RuntimeException{
    public NotFoundException() {
        super();
    }

    public NotFoundException(String exception) {
        super(exception);
    }
}
