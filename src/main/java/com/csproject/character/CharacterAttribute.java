package com.csproject.character;

import com.csproject.exceptions.character.CharacterAttributeUpdateException;
import com.csproject.exceptions.character.CharacterAttributeValueException;

public class CharacterAttribute {

    private static final int MINIMUM = 1;
    private static final int MAXIMUM = 100;

    private final String name;
    private int value;

    public CharacterAttribute(String name, int value) {
        validateValue(value);
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return this.name;
    }

    public int getValue() {
        return this.value;
    }

    public void update(int points) {
        validateUpdate(points);
        this.value += points;
        validateValue(this.value);
    }

    // validates that the value is within the allotted range
    private static void validateValue(int value) {
        if (value < MINIMUM || value > MAXIMUM) {
            throw new CharacterAttributeValueException(value);
        }
    }

    // validate the points given in the update method are strictly positive
    private static void validateUpdate(int points) {
        if (points < 0) throw new CharacterAttributeUpdateException(points);
    }
}
