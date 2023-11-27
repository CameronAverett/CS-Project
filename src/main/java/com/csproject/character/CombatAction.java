package com.csproject.character;

import com.csproject.character.player.Player;
import com.csproject.game.Game;

public record CombatAction(String attack, double damage, double chance, boolean hit) {

    public CombatAction {
        assert chance > 0.0 && chance <= 1.0;
    }

    public CombatAction(String attack, double damage, double chance) {
        this(attack, damage, chance, roll(chance));
    }

    public static boolean roll(double chance) {
        return Game.getRandom().nextDouble(0.0, 1.0) < chance;
    }


    private static String getCharacterTag(Character character) {
        String characterTag = character.getClass().getSimpleName();
        if (character instanceof Player player) {
            characterTag = player.getName();
        }
        return characterTag;
    }

    private String getHitMessage(String target) {
        return String.format("%n%s took %.2f damage.", target, damage);
    }

    public void displayAction(Character self, Character target) {
        String selfTag = getCharacterTag(self);
        String targetTag = getCharacterTag(target);
        String action = hit ? "hit" : "missed";
        String consequence = hit ? getHitMessage(targetTag) : "";

        System.out.printf(
                """
                %s used %s on %s and %s. %s
                """,
                selfTag, attack, targetTag,
                action, consequence
        );
    }
}
