package com.csproject.character.player;

import com.csproject.character.CombatAction;
import com.csproject.character.SaveAction;
import com.csproject.exceptions.character.CombatResponseException;
import com.csproject.game.Game;
import com.csproject.game.GameResponse;

import java.util.List;

public class Archer extends Player {

    private static final String SHOOT_ARROW = "Shoot Arrow";
    private static final String PRECISION_SHOT = "Precision Shot";
    private static final String EVASIVE_MANEUVER = "Evasive Maneuver";

    public Archer(String name, int level, int strength, int intelligence, int agility) {
        super(name, level, strength, intelligence, agility);
    }
    
    public CombatAction shootArrow() {
        return new CombatAction(SHOOT_ARROW, getStrength() + getAgility(), 0.5);
    }
    
    public CombatAction precisionShot() {
        return new CombatAction(PRECISION_SHOT, 10.0, getIntelligence() >= 15);
    }
    
    public SaveAction evasiveManeuver() {
        double damageReduction = Game.calculatePlayerChance(getIntelligence() + getAgility(), 1.0);
        double chance = Game.calculatePlayerChance(2 * getAgility(), 0.95);
        return new SaveAction(EVASIVE_MANEUVER, damageReduction, chance);
    }

     @Override
    public CombatAction combat() {
        GameResponse response = new GameResponse("Which move do you want to use? ");
        response.setResponses(List.of(SHOOT_ARROW, PRECISION_SHOT));

        response.displayResponses("\nAvailable Moves");
        String responseValue = response.getResponse();

        switch (responseValue) {
            case SHOOT_ARROW -> {
                return shootArrow();
            }
            case PRECISION_SHOT -> {
                return precisionShot();
            }
            default -> throw new CombatResponseException(responseValue);
        }
    }

    @Override
    public SaveAction saveChance() {
        return evasiveManeuver();
    }
}
