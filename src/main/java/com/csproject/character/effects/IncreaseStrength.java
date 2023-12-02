package com.csproject.character.effects;

import com.csproject.exceptions.character.effects.EffectStatException;

public class IncreaseStrength extends StatusEffect {

    private final double strength;

    public IncreaseStrength(int turns, double strength) {
        super(turns);
        if (strength < 1.0) throw new EffectStatException("IncreaseStrength", "Strength", strength);
        this.strength = strength;
    }

    @Override
    public Effect applyEffect() {
        super.applyEffect();
        return new Effect(0.0, strength, 0.0, 0.0);
    }
}
