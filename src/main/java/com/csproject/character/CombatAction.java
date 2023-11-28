package com.csproject.character;

import com.csproject.character.effects.NoEffect;
import com.csproject.character.effects.StatusEffect;
import com.csproject.character.player.Player;
import com.csproject.game.Game;


public record CombatAction(String attack, double damage, double chance, StatusEffect effect, boolean hit, CombatActionDisplay display) {

    private static final String SUCCESS_IDENTIFIER = "HIT";
    private static final String FAILURE_IDENTIFIER = "MISSED";

    public CombatAction {
        assert chance > 0.0 && chance <= 1.0;
    }

    public CombatAction(String attack, double damage, double chance, StatusEffect effect, boolean hit) {
        this(attack, damage, chance, effect, hit, new CombatActionDisplay());
    }

    public CombatAction(String attack, double damage, double chance, CombatActionDisplay display) {
        this(attack, damage, chance, new NoEffect(), roll(chance), display);
    }

    public CombatAction(String attack, double damage, double chance) {
        this(attack, damage, chance, new NoEffect(), roll(chance));
    }

    public CombatAction(String attack, double damage, boolean hit, CombatActionDisplay display) {
        this(attack, damage, 0.0, new NoEffect(), hit, display);
    }

    public CombatAction(String attack, double damage, boolean hit) {
        this(attack, damage, 0.0, new NoEffect(), hit);
    }

    public CombatAction(String attack, StatusEffect effect, boolean hit, CombatActionDisplay display) {
        this(attack, 0.0, 0.0, effect, hit, display);
    }

    public CombatAction(String attack, StatusEffect effect, boolean hit) {
        this(attack, 0.0, 0.0, effect, hit);
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

    public void displayAction(Character self, Character target) {
        String selfTag = getCharacterTag(self);
        String targetTag = getCharacterTag(target);
        String action = hit ? SUCCESS_IDENTIFIER : FAILURE_IDENTIFIER;
        String consequence = hit ? display().getSuccessMessage(selfTag, targetTag, damage) :
                display.getFailureMessage(selfTag, targetTag, damage);

        System.out.printf(
                """
                
                %s used %s on %s and %s [%.2f]. %s
                """,
                selfTag, attack, targetTag,
                action, chance * 100, consequence
        );
    }

    private static double erf(double z) {
        double t = 1.0 / (1.0 + 0.47047 * Math.abs(z));
        double poly = t * (0.3480242 + t * (-0.0958798 + t * (0.7478556)));
        double ans = 1.0 - poly * Math.exp(-z*z);
        if (z >= 0) return  ans;
        else return -ans;
    }

    // calculates the shaded percentage of a normal distribution function
    // used to scale attack chance based off some double (stat)
    public static double percentage(double std, double mean, double x) {
        double z = (x - mean) / std;
        return 0.5 * (1.0 + erf(z / (Math.sqrt(2.0))));
    }

    public static double percentage(double std, double x) {
        return percentage(std, 3 * std, x);
    }

    public static double calculateChance(double std, double mean, double x, double maxChance) {
        return maxChance * percentage(std, mean, x);
    }

    // https://www.desmos.com/calculator/ssejz0yc7i
    public static double calculateChance(double std, double x, double maxChance) {
        return maxChance * percentage(std, x);
    }
}
