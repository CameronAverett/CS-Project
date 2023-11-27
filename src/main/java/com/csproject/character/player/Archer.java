package com.csproject.character.player;

import com.csproject.character.CombatAction;

public class Archer extends Player {

    public Archer(String name, int level, int strength, int intelligence, int agility) {
        super(name, level, strength, intelligence, agility);
    }
    
    public void shootArrow() {
        int arrowDamage = getStrength() + getAgility();
    }
    
    public void precisionShot() {
        if (getIntelligence() >= 15) {
            System.out.println(name + " performs a precision shot!");
        } else {
            System.out.println(name + "'s precision shot fails due to low intelligence.");
        }
    }
    
    public void evasiveManeuver() {
        if (getAgility() >= 20) {
            System.out.println(name + " performs an evasive maneuver!");
        } else {
            System.out.println(name + " attempts an evasive maneuver but fails due to low agility.");
        }
    }

     @Override
    public CombatAction combat() {
        GameResponse response = new GameResponse(Game.getInstance().getIn(), "Which move do you want to use? ");
        response.setResponses(List.of("Shoot Arrow", "Precision Shot", "Evasive Maneuver"));

        response.displayResponses("\nAvailable Moves");
        String responseValue = response.getResponse();

        switch (responseValue) {
            case "Shoot Arrow" -> {
                return shootArrow();
            }
            case "Precision Shot" -> {
                return precisionShot();
            }
            case "Evasive Maneuver" -> {
                return evasiveManeuver();
            }
            default -> throw new CombatResponseException(responseValue);
        }
    }
}
