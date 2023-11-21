package com.csproject.character.player;

import com.csproject.character.Character;

public class Player extends Character {

    private double score = 0.0;

    public Player( int level, int strength, int intelligence, int agility) {
        super(level, strength, intelligence, agility);
    }
}
