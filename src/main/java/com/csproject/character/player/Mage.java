package com.csproject.character.player;

import com.csproject.character.CombatAction;
import com.csproject.character.SaveAction;
import com.csproject.exceptions.character.CombatResponseException;
import com.csproject.game.Game;
import com.csproject.game.GameResponse;

import java.util.List;

public class Mage extends Player {

    private static final String CAST_FIREBALL = "Cast Fireball";
    private static final String SUMMON_ICE_BARRIER = "Summon Ice Barrier";
    private static final String TELEPORT = "Teleport";

    public Mage(String name, int level, int strength, int intelligence, int agility) {
        super(name, level, strength, intelligence, agility);
    }

    public CombatAction castFireball() {
        return new CombatAction(CAST_FIREBALL, 2 * getAgility(), 0.5);
    }

    public CombatAction summonIceBarrier() {
        return new CombatAction(SUMMON_ICE_BARRIER, getAgility() * 3, 0.5);
    }

    public SaveAction teleport() {
        double damageReduction = Game.calculatePlayerChance(getIntelligence() + getAgility(), 1.0);
        double chance = Game.calculatePlayerChance(getAgility(), 0.95);
        return new SaveAction(TELEPORT, damageReduction, chance);
    }
    
    @Override
    public CombatAction combat() {
        GameResponse response = new GameResponse("Which move do you want to use? ");
        response.setResponses(List.of(CAST_FIREBALL, SUMMON_ICE_BARRIER));

        response.displayResponses("\nAvailable Moves");
        String responseValue = response.getResponse();

        switch (responseValue) {
            case CAST_FIREBALL -> {
                return castFireball();
            }
            case SUMMON_ICE_BARRIER -> {
                return summonIceBarrier();
            }
            default -> throw new CombatResponseException(responseValue);
        }
    }

    @Override
    public SaveAction saveChance() {
        return teleport();
    }
}
