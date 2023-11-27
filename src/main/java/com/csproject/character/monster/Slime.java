package com.csproject.character.monster;

import com.csproject.character.CombatAction;

public class Slime extends Monster {
    public Slime(double xp, int level, int strength, int intelligence, int agility) {
        super(xp, level, strength, intelligence, agility);
    }

    @Override
    public CombatAction combat() {
        return null;
    }
}
