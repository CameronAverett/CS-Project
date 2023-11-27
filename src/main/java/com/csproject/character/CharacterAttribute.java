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

    public CharacterAttribute(CharacterAttribute base) {
        this.name = base.name;
        this.value = base.value;
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

    private static void validateValue(int value) {
        if (value < MINIMUM || value > MAXIMUM) {
            throw new CharacterAttributeValueException(value);
        }
    }

    private static void validateUpdate(int points) {
        if (points < 0) throw new CharacterAttributeUpdateException(points);
    }
}
