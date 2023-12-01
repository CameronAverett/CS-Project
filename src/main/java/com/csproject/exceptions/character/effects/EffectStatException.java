package com.csproject.exceptions.character.effects;

public class EffectStatException extends RuntimeException {
    public EffectStatException(String effect, String stat, double value) {
        super(String.format("Effect %s received invalid value %.2f for stat %s.", effect, value, stat));
    }
}
