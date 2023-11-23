package com.csproject.character.effects;

public class Burning extends StatusEffect {
    public Burning() {
        super(3);
    }

    @Override
    public Effect applyEffect() {
        super.applyEffect();
        return new Effect(
                1.0/16.0,
                0.1,
                0.0,
                0.0
        );
    }
}
