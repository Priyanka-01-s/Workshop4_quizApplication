package com.exceptions;

public class ParticipantNotFoundException extends Exception {
    public ParticipantNotFoundException(String message) {
        super(message);
    }
}