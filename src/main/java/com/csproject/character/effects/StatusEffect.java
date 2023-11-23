package com.csproject.character.effects;

public abstract class StatusEffect {

    private final int turns;

    private int turnCounter = 0;

    protected StatusEffect(int turns) {
        this.turns = turns;
    }

    public Effect applyEffect() {
        turnCounter++;
        return Effect.noEffect();
    }

    public boolean remove() {
        return turnCounter >= turns;
    }
}
