package com.csproject.character.monster;

import com.csproject.character.CombatAction;

public class Slime extends Monster {

    private static final double BASE_XP = 10.0;
    private static final double XP_PER_LEVEL = 6.5;

    public Slime(int level, int strength, int intelligence, int agility) {
        super(BASE_XP + (XP_PER_LEVEL * (level - 1)), level, strength, intelligence, agility);
    }

    @Override
    public CombatAction combat() {
        return null;
    }
}
