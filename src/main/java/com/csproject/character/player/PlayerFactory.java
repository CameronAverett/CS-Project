package com.csproject.character.player;

import com.csproject.exceptions.character.player.PlayerClassException;

public class PlayerFactory {

    private PlayerFactory() {}

    public static Player get(String playerClass, String name, int level, int strength, int intelligence, int agility) {
        switch (playerClass.toLowerCase()) {
            case "warrior" -> {
                return new Warrior(name, level, strength, intelligence, agility);
            }
            case "mage" -> {
                return new Mage(name, level, strength, intelligence, agility);
            }
            case "archer" -> {
                return new Archer(name, level, strength, intelligence, agility);
            }
            default -> throw new PlayerClassException(playerClass);
        }
    }
}
