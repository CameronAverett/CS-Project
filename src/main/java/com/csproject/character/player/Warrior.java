package com.csproject.character.player;

import com.csproject.character.CombatAction;
import com.csproject.exceptions.character.CombatResponseException;
import com.csproject.game.Game;
import com.csproject.game.GameResponse;

import java.util.List;

public class Warrior extends Player {

    public Warrior(String name, int level, int strength, int intelligence, int agility) {
        super(name, level, strength, intelligence, agility);
    }
    public CombatAction attack() {
        double attackDamage = getStrength() * 2;
        return new CombatAction("Attack", attackDamage, 0.9);
    }

    public CombatAction shieldBlock() {
        int blockStrength = getStrength() + getAgility();
        return new CombatAction("Shield", blockStrength, 0.5);
    }

    public CombatAction berserkerRage() {
        if (getStrength() >= 15 && getLevel() >= 10) {
            System.out.println(name + " enters a berserker rage, gaining increased strength!");
        } else {
            System.out.println(name + " attempts to enter a berserker rage but fails.");
        }
        return new CombatAction("Rage", 0.0, 0.0, getStrength() >= 15 && getLevel() >= 10);
    }

    @Override
    public CombatAction combat() {
        GameResponse response = new GameResponse(Game.getInstance().getIn(), "Which move do you want to use? ");
        response.setResponses(List.of("Attack", "Shield", "Rage"));

        response.displayResponses("\nAvailable Moves");
        String responseValue = response.getResponse();

        switch (responseValue) {
            case "attack" -> {
                return attack();
            }
            case "shield" -> {
                return shieldBlock();
            }
            case "rage" -> {
                return berserkerRage();
            }
            default -> throw new CombatResponseException(responseValue);
        }
    }
}
