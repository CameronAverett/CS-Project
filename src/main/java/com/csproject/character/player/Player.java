package com.csproject.character.player;

import com.csproject.character.Character;

public abstract class Player extends Character {

    private double score = 0.0;

    private String name;

    protected Player(String name, int level, int strength, int intelligence, int agility) {
        super(level, strength, intelligence, agility);
        this.name = name;
    }
}
