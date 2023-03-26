package com.bw.reference.exception;

/**
 * @author Neme Iloeje niloeje@byteworks.com.ng
 */
public class RecordInUseException extends RuntimeException {

    public RecordInUseException() {
        super();
    }

    public RecordInUseException(String message) {
        super(message);
    }
}
