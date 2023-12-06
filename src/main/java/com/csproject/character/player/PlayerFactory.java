package com.csproject.character.player;

import com.csproject.exceptions.character.player.PlayerClassException;

import java.util.List;

public class PlayerFactory {

    private static final String WARRIOR = "Warrior";
    private static final String MAGE = "Mage";
    private static final String ARCHER = "Archer";

    public static final List<String> PLAYER_CLASSES = List.of(WARRIOR, MAGE, ARCHER);

    private PlayerFactory() {}

    // Method that follows the java factory pattern to create a player
    public static Player get(String playerClass, String name, int level, int strength, int intelligence, int agility) {
        switch (playerClass) {
            case WARRIOR -> {
                return new Warrior(name, level, strength, intelligence, agility);
            }
            case MAGE -> {
                return new Mage(name, level, strength, intelligence, agility);
            }
            case ARCHER -> {
                return new Archer(name, level, strength, intelligence, agility);
            }
            default -> throw new PlayerClassException(playerClass);
        }
    }
}
