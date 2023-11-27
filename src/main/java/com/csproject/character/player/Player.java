package com.csproject.character.player;

import com.csproject.character.Character;
import com.csproject.character.monster.Monster;

public abstract class Player extends Character {

    private static final double BASE_EXP = 100.0;
    private static final double EXP_PER_LEVEL = 10.0;

    private static final double LEVEL_DIFFERENCE_RATE = 1.7;
    private static final double DIFFERENCE_SCALE_RATE = 3.0;

    protected String name;

    private double score = 0.0;
    private double exp = 0.0;

    protected Player(String name, int level, int strength, int intelligence, int agility) {
        super(level, strength, intelligence, agility);
        this.name = name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    @Override
    public void displayStats() {
        System.out.printf(
                """
                
                ==============================
                Name: %s
                Class: %s
                Score: %.2f
                ==============================
                Lv: %d
                Exp: %.2f%%
                Hp: %s
                Mp: %s
                ==============================
                Strength: %d
                Intelligence: %d
                Agility: %d
                ==============================
                """, name, this.getClass().getSimpleName(), score, getLevel(),
                exp, getHp() + "/" + getMaxHp(), getMana() + "/" + getMaxMana(),
                getStrength(), getIntelligence(), getAgility()
        );
    }

    public String getName() {
        return name;
    }

    public double getMaxExp() {
        return BASE_EXP + (EXP_PER_LEVEL * (getLevel() - 1));
    }

    public void increaseScore(double earned) {
        this.score += earned;
    }

    public void earnExp(Monster enemy) {
        double numerator = LEVEL_DIFFERENCE_RATE * (enemy.getLevel() - getLevel());
        double denominator = DIFFERENCE_SCALE_RATE * enemy.getLevel();
        exp += enemy.getXp() * Math.exp(numerator / denominator);
        if (exp >= getMaxExp()) {
            exp -= getMaxExp();
            levelUp();
        }
    }
}
