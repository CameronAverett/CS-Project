package com.csproject.character;

public record ActionDisplay(String success, String failure) {

    public static final String DEFAULT_COMBAT_SUCCESS = "\n[TARGET] received [DAMAGE] damageReduction.";
    public static final String DEFAULT_COMBAT_FAILURE = "";

    public static final String DEFAULT_SAVE_SUCCESS = "\n[SELF] stopped [DAMAGE] from [TARGET]'s attack.";
    public static final String DEFAULT_SAVE_FAILURE = "";

    public static final String SELF_IDENTIFIER = "(\\[SELF])";
    public static final String TARGET_IDENTIFIER = "(\\[TARGET])";
    public static final String DAMAGE_IDENTIFIER = "(\\[DAMAGE])";


    public static ActionDisplay defaultCombat() {
        return new ActionDisplay(DEFAULT_COMBAT_SUCCESS, DEFAULT_COMBAT_FAILURE);
    }

    public static ActionDisplay defaultSave() {
        return new ActionDisplay(DEFAULT_SAVE_SUCCESS, DEFAULT_SAVE_FAILURE);
    }

    public String getSuccessMessage(String self, String target, double damage) {
        String message = success;
        message = message.replaceAll(SELF_IDENTIFIER, self);
        message = message.replaceAll(TARGET_IDENTIFIER, target);
        message = message.replaceAll(DAMAGE_IDENTIFIER, String.format("%.2f", damage));
        return message;
    }

    public String getFailureMessage(String self, String target, double damage) {
        String message = failure;
        message = message.replaceAll(SELF_IDENTIFIER, self);
        message = message.replaceAll(TARGET_IDENTIFIER, target);
        message = message.replaceAll(DAMAGE_IDENTIFIER, String.format("%.2f", damage));
        return message;
    }
}
