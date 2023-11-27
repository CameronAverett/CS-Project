package com.csproject.character.player;

import com.csproject.character.CombatAction;
import com.csproject.character.effects.NoEffect;
import com.csproject.exceptions.character.CombatResponseException;
import com.csproject.game.Game;
import com.csproject.game.GameResponse;

import java.util.List;

public class Warrior extends Player {

    private static final String ATTACK = "Attack";
    private static final String SHIELD = "Shield";
    private static final String RAGE = "Rage";

    public Warrior(String name, int level, int strength, int intelligence, int agility) {
        super(name, level, strength, intelligence, agility);
    }
    public CombatAction attack() {
        double attackDamage = getStrength() * 2;
        return new CombatAction(ATTACK, attackDamage, 0.9);
    }

    public CombatAction shieldBlock() {
        int blockStrength = getStrength() + getAgility();
        return new CombatAction(SHIELD, blockStrength, 0.5);
    }

    public CombatAction berserkerRage() {
        // William: I'll add a way to add custom success messages
        return new CombatAction(RAGE, new NoEffect(), getStrength() >= 15 && getLevel() >= 10);
    }

    @Override
    public CombatAction combat() {
        GameResponse response = new GameResponse(Game.getInstance().getIn(), "Which move do you want to use? ");
        response.setResponses(List.of(ATTACK, SHIELD, RAGE));

        response.displayResponses("\nAvailable Moves");
        String responseValue = response.getResponse();

        switch (responseValue) {
            case ATTACK -> {
                return attack();
            }
            case SHIELD -> {
                return shieldBlock();
            }
            case RAGE -> {
                return berserkerRage();
            }
            default -> throw new CombatResponseException(responseValue);
        }
    }
}
