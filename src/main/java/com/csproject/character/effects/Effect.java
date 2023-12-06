package com.csproject.character.effects;

public record Effect(double damage, double strength, double intelligence, double agility) {
    public static Effect noEffect() {
        return new Effect(0, 0, 0, 0);
    }

    // asserts that all effect fields are non-negative
    public Effect {
        assert damage >= 0.0;
        assert strength >= 0.0;
        assert intelligence >= 0.0;
        assert agility >= 0.0;
    }
}
