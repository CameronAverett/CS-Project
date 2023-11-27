package com.csproject.character.player;

import com.csproject.character.CombatAction;

public class Warrior extends Player {

    public Warrior(String name, int level, int strength, int intelligence, int agility) {
        super(name, level, strength, intelligence, agility);
    }
     public void attack() {
        int attackDamage = getStrength() * 2;
    }

    public void shieldBlock() {
        int blockStrength = getStrength() + getAgility();
    }

    public void berserkerRage() {
        if (getStrength() >= 15 && getLevel() >= 10) {
            System.out.println(name + " enters a berserker rage, gaining increased strength!");
        } else {
            System.out.println(name + " attempts to enter a berserker rage but fails.");
        }
    }

    @Override
    public CombatAction combat() {
        return null;
    }
}
