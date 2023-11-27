package com.csproject.character;

import com.csproject.character.effects.StatusEffect;
import com.csproject.game.Game;

import java.util.ArrayList;
import java.util.List;

public abstract class Character {

    private static final double BASE_HP = 100;
    private static final double HP_PER_LEVEL = 10;

    private static final double MANA_PER_INTELLIGENCE = 25;

    private double hp;
    private double mana;
    private int level;

    protected final CharacterAttribute strength;
    protected final CharacterAttribute intelligence;
    protected final CharacterAttribute agility;

    protected List<StatusEffect> statusEffects = new ArrayList<>();

    protected Character(int level, int strength, int intelligence, int agility) {
        this.level = level;
        this.strength = new CharacterAttribute("strength", strength);
        this.intelligence = new CharacterAttribute("intelligence", intelligence);
        this.agility = new CharacterAttribute("agility", agility);

        this.hp = getMaxHp();
        this.mana = getMaxMana();
    }

    public abstract CombatAction combat();

    public abstract void displayStats();

    public double getHp() {
        return this.hp;
    }

    public void dealDamage(double damage) {
        this.hp -= damage;
    }

    public void heal(double health) {
        this.hp += health;
        if (this.hp > this.getMaxHp()) {
            this.hp = getMaxHp();
        }
    }

    public double getMaxHp() {
        return BASE_HP + (HP_PER_LEVEL * (this.level - 1));
    }

    public boolean isDead() {
        return this.hp < 0.0;
    }

    public double getMana() {
        return this.mana;
    }

    public void regenMana(double mana) {
        this.mana += mana;
        if (this.mana > this.getMaxMana()) {
            this.mana = getMaxMana();
        }
    }

    public double getMaxMana() {
        return MANA_PER_INTELLIGENCE * intelligence.getValue();
    }

    public int getLevel() {
        return this.level;
    }

    public void levelUp() {
        this.level++;
    }

    public int getStrength() {
        return strength.getValue();
    }

    public int getIntelligence() {
        return intelligence.getValue();
    }

    public int getAgility() {
        return agility.getValue();
    }

    public List<StatusEffect> getStatusEffects() {
        return this.statusEffects;
    }

    public static int[] generateStats(int min, int bound, int size) {
        int[] genStats = new int[size];
        for(int i = 0; i < size; i++) {
            genStats[i] = Game.getRandom().nextInt(min, bound);
        }
        return genStats;
    }
}
