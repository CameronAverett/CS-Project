package com.csproject.character;

import com.csproject.character.effects.NoEffect;
import com.csproject.character.effects.StatusEffect;
import com.csproject.character.player.Player;
import com.csproject.game.Game;


public record CombatAction(String action, double damage, double chance, StatusEffect effect, boolean hit, ActionDisplay display) {

    private static final String SUCCESS_IDENTIFIER = "HIT";
    private static final String FAILURE_IDENTIFIER = "MISSED";

    public CombatAction {
        assert chance > 0.0 && chance <= 1.0;
    }

    public CombatAction(String attack, double damage, double chance, StatusEffect effect, boolean hit) {
        this(attack, damage, chance, effect, hit,  ActionDisplay.defaultCombat());
    }

    public CombatAction(String attack, double damage, double chance, ActionDisplay display) {
        this(attack, damage, chance, new NoEffect(), roll(chance), display);
    }

    public CombatAction(String attack, double damage, double chance) {
        this(attack, damage, chance, new NoEffect(), roll(chance));
    }

    public CombatAction(String attack, double damage, boolean hit, ActionDisplay display) {
        this(attack, damage, 0.0, new NoEffect(), hit, display);
    }

    public CombatAction(String attack, double damage, boolean hit) {
        this(attack, damage, 0.0, new NoEffect(), hit);
    }

    public CombatAction(String attack, StatusEffect effect, boolean hit, ActionDisplay display) {
        this(attack, 0.0, 0.0, effect, hit, display);
    }

    public CombatAction(String attack, StatusEffect effect, boolean hit) {
        this(attack, 0.0, 0.0, effect, hit);
    }

    public CombatAction(String attack, boolean hit) {
        this(attack, new NoEffect(), hit);
    }

    public static boolean roll(double chance) {
        return Game.getRandom().nextDouble(0.0, 1.0001) < chance;
    }


    private static String getCharacterTag(Character character) {
        String characterTag = character.getClass().getSimpleName();
        if (character instanceof Player player) {
            characterTag = player.getName();
        }
        return characterTag;
    }

    public void displayAction(Character self, Character target) {
        String selfTag = getCharacterTag(self);
        String targetTag = getCharacterTag(target);
        String success = hit ? SUCCESS_IDENTIFIER : FAILURE_IDENTIFIER;
        String consequence = hit ? display().getSuccessMessage(selfTag, targetTag, damage) :
                display.getFailureMessage(selfTag, targetTag, damage);

        System.out.printf(
                """
                
                %s used %s on %s and %s [%.2f%%]. %s
                """,
                selfTag, action, targetTag,
                success, chance * 100, consequence
        );
    }
}
