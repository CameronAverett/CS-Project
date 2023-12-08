package com.csproject.character;

import com.csproject.character.player.Player;
import com.csproject.game.Game;

public record SaveAction(String action, double damageReduction, double chance, boolean successful, ActionDisplay display) {

    private static final String SUCCESS_IDENTIFIER = "SUCCEEDED";
    private static final String FAILURE_IDENTIFIER = "FAILED";

    public SaveAction(String action, double damageReduction, double chance, boolean successful) {
        this(action, damageReduction, chance, successful, ActionDisplay.defaultSave());
    }

    public SaveAction(String action, double damageReduction, double chance) {
        this(action, damageReduction, chance, roll(chance));
    }

    public static boolean roll(double chance) {
        return Game.getRandom().nextDouble(0.0, 1.0001) < chance;
    }

    // Get the name of a character class object from the class name if it is a monster and the name field if it is a player
    private static String getCharacterTag(Character character) {
        String characterTag = character.getClass().getSimpleName();
        if (character instanceof Player player) {
            characterTag = player.getName();
        }
        return characterTag;
    }

    // Method to display the action and should be called after it is returned
    public void displayAction(Character self, Character target) {
        String selfTag = getCharacterTag(self);
        String targetTag = getCharacterTag(target);
        String success = successful ? SUCCESS_IDENTIFIER : FAILURE_IDENTIFIER;
        String consequence = successful ? display().getSuccessMessage(selfTag, targetTag, damageReduction * 100) :
                display.getFailureMessage(selfTag, targetTag, damageReduction * 100);

        System.out.printf(
                """
                %s used %s and %s [%.2f%%]. %s
                """,
                selfTag, action, success,
                chance * 100, consequence
        );
    }
}
