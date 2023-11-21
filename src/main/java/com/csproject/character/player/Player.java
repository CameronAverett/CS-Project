package com.csproject.character.player;

import com.csproject.character.Character;
import com.csproject.game.Difficulty;

public class Player extends Character {

    private double score = 0.0;

    private String name;
    private Difficulty difficulty;

    public Player(String name, Difficulty difficulty, int level, int strength, int intelligence, int agility) {
        super(level, strength, intelligence, agility);
        this.name = name;
        this.difficulty = difficulty;
    }
}
