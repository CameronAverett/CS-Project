package com.csproject.exceptions.character;

public class CharacterAttributeValueException extends RuntimeException {
    public CharacterAttributeValueException(int value) {
        super(String.format("Invalid character attribute value: %d", value));
    }
}
