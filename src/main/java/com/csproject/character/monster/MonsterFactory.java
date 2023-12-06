package com.csproject.character.monster;

import com.csproject.character.Character;
import com.csproject.exceptions.character.monster.MonsterClassException;

import java.util.Arrays;

public class MonsterFactory {

    private static final String SLIME = "Slime";
    private static final String SKELETON = "Skeleton";
    private static final String ZOMBIE = "Zombie";

    private MonsterFactory() {}

    // Method that follows the java factory pattern to create a monster from a class type and level
    public static Monster get(String monsterClass, int level) {
        int[] stats = Character.generateStats(level, 6 * level + 1, 3);
        Arrays.sort(stats);

        switch (monsterClass) {
            case SLIME -> {
                return new Slime(level, stats[1], stats[0], stats[2]);
            }
            case SKELETON -> {
                return new Skeleton(level, stats[2], stats[0], stats[1]);
            }
            case ZOMBIE -> {
                return new Zombie(level, stats[1], stats[2], stats[0]);
            }
            default -> throw new MonsterClassException(monsterClass);
        }

    }
}
