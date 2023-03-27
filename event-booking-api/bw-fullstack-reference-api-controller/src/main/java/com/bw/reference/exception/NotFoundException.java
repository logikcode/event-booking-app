package com.bw.reference.exception;

/**
 * @author Neme Iloeje niloeje@byteworks.com.ng
 */
public class NotFoundException extends RuntimeException {

    public NotFoundException() {
        super();
    }

    public NotFoundException(String message) {
        super(message);
    }
}
