package com.csproject.character;

import com.csproject.game.Difficulty;

public abstract class Character {

    private static final double BASE_HP = 100;
    private static final double HP_PER_LEVEL = 10;

    private static final double MANA_PER_INTELLIGENCE = 25;

    private double hp;
    private double mana;
    private int level;
    private Difficulty difficulty;

    protected final CharacterAttribute strength;
    protected final CharacterAttribute intelligence;
    protected final CharacterAttribute agility;

    protected Character(int level, Difficulty difficulty, int strength, int intelligence, int agility) {
        this.level = level;
        this.difficulty = difficulty;
        this.strength = new CharacterAttribute("strength", strength);
        this.intelligence = new CharacterAttribute("intelligence", intelligence);
        this.agility = new CharacterAttribute("agility", agility);

        this.hp = getMaxHp();
        this.mana = getMaxMana();
    }

    public double getHp() {
        return this.hp;
    }

    public void dealDamage(double damage) {
        this.hp -= damage;
    }

    public void heal() {
        this.hp = getMaxHp();
    }

    public void levelUp() {
        this.level++;
    }

    private double getMaxHp() {
        return BASE_HP + (HP_PER_LEVEL * (this.level - 1));
    }

    private double getMana() {
        return this.mana;
    }

    private double getMaxMana() {
        return MANA_PER_INTELLIGENCE * intelligence.getValue();
    }

    public boolean isDead() {
        return this.hp < 0.0;
    }
}
