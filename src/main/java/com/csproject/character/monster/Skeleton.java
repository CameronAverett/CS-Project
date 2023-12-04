package com.csproject.character.monster;

import com.csproject.character.CombatAction;
import com.csproject.character.SaveAction;

public class Skeleton extends Monster {

    private static final double BASE_XP = 12.0;
    private static final double XP_PER_LEVEL = 7.0;

    public Skeleton(int level, int strength, int intelligence, int agility) {
        super(BASE_XP + (XP_PER_LEVEL * (level - 1)), level, strength, intelligence, agility);
    }

    @Override
    public CombatAction combat() {
        return null;
    }

    @Override
    public SaveAction saveChance() {
        return null;
    }
}
