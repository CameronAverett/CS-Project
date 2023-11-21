package com.csproject.character.monster;

import com.csproject.character.Character;
import com.csproject.game.Difficulty;

public class Monster extends Character {

    private double xp;
    private Difficulty difficulty;

    public Monster(double xp, Difficulty difficulty, int level, int strength, int intelligence, int agility) {
        super(level, strength, intelligence, agility);
        this.xp = xp;
        this.difficulty = difficulty;
    }
}
