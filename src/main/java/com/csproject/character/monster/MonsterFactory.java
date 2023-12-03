package com.csproject.character.monster;

import com.csproject.character.Character;
import com.csproject.exceptions.character.monster.MonsterClassException;

import java.util.Arrays;

public class MonsterFactory {

    private static final String SLIME = "Slime";

    private MonsterFactory() {}

    public static Monster get(String monsterClass, int level) {
        int[] stats = Character.generateStats(level, 6 * level + 1, 3);
        Arrays.sort(stats);

        switch (monsterClass) {
            case SLIME -> {
                return new Slime(level, stats[1], stats[0], stats[2]);
            }
            default -> throw new MonsterClassException(monsterClass);
        }

    }
}
