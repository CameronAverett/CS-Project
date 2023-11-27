package com.csproject.character;

public record CombatActionDisplay(String success, String failure) {

    public static final String DEFAULT_SUCCESS = "\n[TARGET] received [DAMAGE] damage.";
    public static final String DEFAULT_FAILURE = "";

    public static final String SELF_IDENTIFIER = "(\\[SELF])";
    public static final String TARGET_IDENTIFIER = "(\\[TARGET])";
    public static final String DAMAGE_IDENTIFIER = "(\\[DAMAGE])";

    public CombatActionDisplay() {
        this(DEFAULT_SUCCESS, DEFAULT_FAILURE);
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
