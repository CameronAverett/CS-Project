package com.csproject.character.player;

import com.csproject.character.CombatAction;
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
    
    public CombatAction evasiveManeuver() {
        return new CombatAction(EVASIVE_MANEUVER, 0.0, getAgility() >= 20);
    }

     @Override
    public CombatAction combat() {
        GameResponse response = new GameResponse(Game.getInstance().getIn(), "Which move do you want to use? ");
        response.setResponses(List.of(SHOOT_ARROW, PRECISION_SHOT, EVASIVE_MANEUVER));

        response.displayResponses("\nAvailable Moves");
        String responseValue = response.getResponse();

        switch (responseValue) {
            case SHOOT_ARROW -> {
                return shootArrow();
            }
            case PRECISION_SHOT -> {
                return precisionShot();
            }
            case EVASIVE_MANEUVER -> {
                return evasiveManeuver();
            }
            default -> throw new CombatResponseException(responseValue);
        }
    }
}
