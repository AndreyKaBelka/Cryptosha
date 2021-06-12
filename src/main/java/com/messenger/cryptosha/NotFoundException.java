package com.messenger.cryptosha;

public class NotFoundException extends RuntimeException{
    public NotFoundException() {
        super();
    }

    public NotFoundException(String exception) {
        super(exception);
    }
}
