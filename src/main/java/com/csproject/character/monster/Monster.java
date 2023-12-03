package com.csproject.character.monster;

import com.csproject.character.Character;
import com.csproject.game.Game;

public abstract class Monster extends Character {

    private static final double LEVEL_DIFFERENCE_RATE = 1.7;
    private static final double DIFFERENCE_SCALE_RATE = 3.0;

    private final double xp;

    protected Monster(double xp, int level, int strength, int intelligence, int agility) {
        super(level, strength, intelligence, agility);
        this.xp = calculateXp(level, xp);
    }

    @Override
    public void displayStats() {
        System.out.printf(
                """
                
                ==============================
                Class: %s
                Xp: %.2f
                ==============================
                Lv: %d
                Hp: %.2f%%
                Mp: %.2f%%
                ==============================
                Strength: %d
                Intelligence: %d
                Agility: %d
                ==============================
                """, this.getClass().getSimpleName(), xp, getLevel(),
                (getHp() / getMaxHp()) * 100, (getMana() / getMaxMana()) * 100,
                strengthAttr.getValue(), intelligenceAttr.getValue(), agilityAttr.getValue()
        );
    }

    public double getXp() {
        return xp;
    }

    // Method used to calculate xp earned based on the level difference between enemy and player
    // returns xp * exp([multiplier * (enemy level - player level)] / [another multiplier * enemy level])
    private static double calculateXp(int level, double xp) {
        int playerLevel = Game.getInstance().getPlayerLevel();
        double numerator = LEVEL_DIFFERENCE_RATE * (level - playerLevel);
        double denominator = DIFFERENCE_SCALE_RATE * level;
        return xp * Math.exp(numerator / denominator);
    }
}
