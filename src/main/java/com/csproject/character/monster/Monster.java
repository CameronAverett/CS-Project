package com.csproject.character.monster;

import com.csproject.character.Character;

public abstract class Monster extends Character {

    private double xp;

    protected Monster(double xp, int level, int strength, int intelligence, int agility) {
        super(level, strength, intelligence, agility);
        this.xp = xp;
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
                Hp: %s
                Mp: %s
                ==============================
                Strength: %d
                Intelligence: %d
                Agility: %d
                ==============================
                """, this.getClass().getSimpleName(), xp, getLevel(),
                getHp() + "/" + getMaxHp(), getMana() + "/" + getMaxMana(),
                getStrength(), getIntelligence(), getAgility()
        );
    }
}
