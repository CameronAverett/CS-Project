package com.csproject.character.monster;

import com.csproject.exceptions.character.monster.MonsterClassException;

public class MonsterFactory {

    private static final String SLIME = "Slime";

    private MonsterFactory() {}

    public static Monster get(String monsterClass, int level, int strength, int intelligence, int agility) {
        switch (monsterClass) {
            case SLIME -> {
                return new Slime(level, strength, intelligence, agility);
            }
            default -> throw new MonsterClassException(monsterClass);
        }

    }
}
