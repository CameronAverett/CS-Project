package com.csproject.character;

public abstract class Character {

    private static final double BASE_HP = 100;
    private static final double HP_PER_LEVEL = 10;

    private double hp;
    private int level;

    protected final CharacterAttribute strength;
    protected final CharacterAttribute intelligence;
    protected final CharacterAttribute agility;

    protected Character(int level, int strength, int intelligence, int agility) {
        this.level = level;
        this.hp = getMaxHp();

        this.strength = new CharacterAttribute("strength", strength);
        this.intelligence = new CharacterAttribute("intelligence", intelligence);
        this.agility = new CharacterAttribute("agility", agility);
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

    private double getMaxHp() {
        return BASE_HP + (HP_PER_LEVEL * (this.level - 1));
    }

    public boolean isDead() {
        return this.hp < 0.0;
    }
}
