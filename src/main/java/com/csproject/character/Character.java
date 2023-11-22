package com.csproject.character;

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

    protected Character(int level, int strength, int intelligence, int agility) {
        this.level = level;
        this.strength = new CharacterAttribute("strength", strength);
        this.intelligence = new CharacterAttribute("intelligence", intelligence);
        this.agility = new CharacterAttribute("agility", agility);

        this.hp = getMaxHp();
        this.mana = getMaxMana();
    }

    public abstract CombatAction combat();

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

    public void levelUp() {
        this.level++;
    }

    public boolean isDead() {
        return this.hp < 0.0;
    }
}
