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
        return null;
    }
}
