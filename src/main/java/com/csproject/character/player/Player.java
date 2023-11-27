package com.csproject.character.player;

import com.csproject.character.Character;

public abstract class Player extends Character {

    private double score = 0.0;

    private String name;

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
                Hp: %s
                Mp: %s
                ==============================
                Strength: %d
                Intelligence: %d
                Agility: %d
                ==============================
                """, name, this.getClass().getSimpleName(), score, getLevel(),
                getHp() + "/" + getMaxHp(), getMana() + "/" + getMaxMana(),
                getStrength(), getIntelligence(), getAgility()
        );
    }
}
