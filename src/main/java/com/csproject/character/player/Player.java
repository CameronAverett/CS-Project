package com.csproject.character.player;

import com.csproject.character.Character;
import com.csproject.character.CombatAction;

public class Player extends Character {

    private double score = 0.0;

    private String name;

    public Player(String name, int level, int strength, int intelligence, int agility) {
        super(level, strength, intelligence, agility);
        this.name = name;
    }

    @Override
    public CombatAction combat() {
        return null;
    }
}
