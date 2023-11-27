package com.csproject.exceptions.character;

public class CombatResponseException extends RuntimeException {
    public CombatResponseException(String response) {
        super(String.format("Accepted unexpected response value: %s", response));
    }
}
