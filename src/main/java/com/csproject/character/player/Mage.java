package com.csproject.character.player;

import com.csproject.character.CombatAction;

public class Mage extends Player {

    public Mage(String name, int level, int strength, int intelligence, int agility) {
        super(name, level, strength, intelligence, agility);
    }
    public void castFireball() {
        int damage = getAgility() * 2;
         return new CombatAction("Cast Fireball", attackDamage, 0.5);
    }
    public void summonIceBarrier() {
        int barrierStrength = getAgility() * 3;
         return new CombatAction("Summon Ice Barrier", blockStrength, 0.5);
    }
    public void teleport() {
        if (getAgility() >= 20) {
            System.out.println(name + " teleports to a new location!");
        } else {
            System.out.println(name + " attempts to teleport but fails due to low agility.");
        }
        return new CombatAction("Teleport", 0.0, getAgility() >= 20);
    }
    
    @Override
    public CombatAction combat() {
        GameResponse response = new GameResponse(Game.getInstance().getIn(), "Which move do you want to use? ");
        response.setResponses(List.of("Cast Fireball", "Summon Ice Barrier", "Teleport"));

        response.displayResponses("\nAvailable Moves");
        String responseValue = response.getResponse();

        switch (responseValue) {
            case "Cast Fireball" -> {
                return castFireball();
            }
            case "Summon Ice Barrier" -> {
                return summonIceBarrier();
            }
            case "Teleport" -> {
                return teleport();
            }
            default -> throw new CombatResponseException(responseValue);
        }
    }
}
