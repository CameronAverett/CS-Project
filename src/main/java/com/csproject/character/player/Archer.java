package com.csproject.character.player;

import com.csproject.character.CombatAction;

public class Archer extends Player {

    public Archer(String name, int level, int strength, int intelligence, int agility) {
        super(name, level, strength, intelligence, agility);
    }

    @Override
    public CombatAction combat() {
        return null;
    }
}
