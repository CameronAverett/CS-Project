package com.csproject.character.player;

import com.csproject.character.CombatAction;

public class Warrior extends Player {

    public Warrior(String name, int level, int strength, int intelligence, int agility) {
        super(name, level, strength, intelligence, agility);
    }

    @Override
    public CombatAction combat() {
        return null;
    }
}
