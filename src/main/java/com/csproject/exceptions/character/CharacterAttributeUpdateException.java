package com.csproject.exceptions.character;

public class CharacterAttributeUpdateException extends RuntimeException {
    public CharacterAttributeUpdateException(int points) {
        super(String.format("Invalid character attribute update points: %d", points));
    }
}
