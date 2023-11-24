package com.csproject.character.player;

import com.csproject.exceptions.character.player.PlayerClassException;

public class PlayerFactory {

    private PlayerFactory() {}

    public static Player get(String playerClass, String name, int level, int strength, int intelligence, int agility) {
        switch (playerClass.toLowerCase()) {
            case "default" -> {
                return new Player(name, level, strength, intelligence, agility);
            }
            default -> throw new PlayerClassException(playerClass);
        }
    }
}
