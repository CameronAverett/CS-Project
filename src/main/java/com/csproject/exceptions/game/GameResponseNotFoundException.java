package com.csproject.exceptions.game;

import com.csproject.game.GameResponse;

import java.util.Arrays;
import java.util.List;

public class GameResponseNotFoundException extends RuntimeException {
    public GameResponseNotFoundException(List<String> validResponses, String response) {
        super(String.format("The accepted response '%s' on in valid responses %s", response, Arrays.toString(validResponses.toArray())));
    }
}
