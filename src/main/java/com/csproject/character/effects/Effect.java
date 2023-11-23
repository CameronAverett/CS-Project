package com.csproject.character.effects;

public record Effect(double damage, double strength, double intelligence, double agility) {
    public static Effect noEffect() {
        return new Effect(0, 0, 0, 0);
    }

    public Effect {
        assert damage < 1.0 && damage >= 0.0;
        assert strength < 1.0 && strength >= 0.0;
        assert intelligence < 1.0 && intelligence >= 0.0;
        assert agility < 1.0 && agility >= 0.0;
    }
}
