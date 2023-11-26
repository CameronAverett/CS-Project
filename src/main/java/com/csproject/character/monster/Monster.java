package com.csproject.character.monster;

import com.csproject.character.Character;
import com.csproject.character.CombatAction;

public class Monster extends Character {

    private double xp;

    protected Monster(double xp, int level, int strength, int intelligence, int agility) {
        super(level, strength, intelligence, agility);
        this.xp = xp;
    }

    @Override
    public CombatAction combat() {
        return null;
    }
}
