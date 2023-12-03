package com.csproject.exceptions.game;

public class GameMapCreationException extends RuntimeException {
    public GameMapCreationException(String process) {
        super(String.format("Unexpected error occurred during Game Map creation: '%s'", process));
    }
}
