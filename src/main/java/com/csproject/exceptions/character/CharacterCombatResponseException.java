package com.csproject.exceptions.character;

public class CharacterCombatResponseException extends RuntimeException {
    public CharacterCombatResponseException(String response) {
        super(String.format("Accepted unexpected response value: %s", response));
    }
}
