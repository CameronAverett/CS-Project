package com.csproject.exceptions.character.player;

public class PlayerClassException extends RuntimeException {
    public PlayerClassException(String playerClass) {
        super(String.format("Invalid player class: %s", playerClass));
    }
}
