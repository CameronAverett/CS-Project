package com.csproject.character.player;

import com.csproject.character.CombatAction;
import com.csproject.character.SaveAction;
import com.csproject.exceptions.character.CharacterCombatResponseException;
import com.csproject.game.Game;
import com.csproject.game.GameResponse;

import java.util.List;

public class Archer extends Player {

    private static final String SHOOT_ARROW = "Shoot Arrow";
    private static final String PRECISION_SHOT = "Precision Shot";
    private static final String HEAL = "Heal";
    private static final String EVASIVE_MANEUVER = "Evasive Maneuver";

    public Archer(String name, int level, int strength, int intelligence, int agility) {
        super(name, level, strength, intelligence, agility);
    }

    private CombatAction shootArrow() {
        double chance = Game.calculatePlayerChance(getStrength() + getAgility(), 0.8);
        return new CombatAction(SHOOT_ARROW, getStrength() + getAgility(), chance);
    }

    private CombatAction precisionShot() {
        return new CombatAction(PRECISION_SHOT, 10.0, getIntelligence() >= 15);
    }

    private CombatAction heal() {
        boolean canUse = getIntelligence() >= 8 && getMana() >= 10.0;
        if (canUse) consumeMana(10.0);
        return new CombatAction(HEAL, canUse);
    }

    private SaveAction evasiveManeuver() {
        double damageReduction = Game.calculatePlayerChance(getIntelligence() + getAgility(), 1.0);
        double chance = Game.calculatePlayerChance(2.0 * getAgility(), 0.95);
        return new SaveAction(EVASIVE_MANEUVER, damageReduction, chance);
    }

    // Handles combat for the Archer class
    @Override
    public CombatAction combat() {
        GameResponse response = new GameResponse("Which move do you want to use? ");
        response.setResponses(List.of(SHOOT_ARROW, PRECISION_SHOT));

        if (getLevel() >= 3) response.addResponse(HEAL);

        response.displayResponses("\nAvailable Moves");
        String responseValue = response.getResponse();

        switch (responseValue) {
            case SHOOT_ARROW -> {
                return shootArrow();
            }
            case PRECISION_SHOT -> {
                return precisionShot();
            }
            case HEAL -> {
                return heal();
            }
            default -> throw new CharacterCombatResponseException(responseValue);
        }
    }

    // Handles save chance for the Archer class
    @Override
    public SaveAction saveChance() {
        return evasiveManeuver();
    }
}
