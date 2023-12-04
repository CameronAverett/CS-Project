package com.csproject.character.monster;

import com.csproject.character.Character;

public abstract class Monster extends Character {

    private static final double BASE_HP = 20.0;

    private final double xp;

    protected Monster(double xp, int level, int strength, int intelligence, int agility) {
        super(BASE_HP, level, strength, intelligence, agility);
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
}
