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
    private static final String HEAL = "Heal";
    private static final String CAST_LIGHTNING = "Cast Lightning";
    private static final String TELEPORT = "Teleport";

    public Mage(String name, int level, int strength, int intelligence, int agility) {
        super(name, level, strength, intelligence, agility);
    }

    private CombatAction castFireball() {
        double chance = Game.calculatePlayerChance(2.0 * getIntelligence(), getMana() >= 5.0 ? 0.80 : 0.0);
        CombatAction action = new CombatAction(CAST_FIREBALL, 2.0 * getIntelligence(), chance);
        if (action.hit()) consumeMana(5.0);
        return action;
    }

    private CombatAction summonIceBarrier() {
        double chance = Game.calculatePlayerChance(getIntelligence() + getAgility(), getMana() >= 7.0 ? 0.85 : 0.0);
        CombatAction action = new CombatAction(SUMMON_ICE_BARRIER, getIntelligence() + getAgility(), chance);
        if (action.hit()) consumeMana(7.0);
        return action;
    }

    private CombatAction heal() {
        boolean canUse = getIntelligence() >= 8 && getMana() >= 10.0;
        if (canUse) consumeMana(10.0);
        return new CombatAction(HEAL, canUse);
    }

    private CombatAction castLightning() {
        double chance = Game.calculatePlayerChance(3.0 * getIntelligence(), getMana() >= 10.0 ? 0.8 : 0.0);
        CombatAction action = new CombatAction(CAST_LIGHTNING, 3.0 * getIntelligence(), chance);
        if (action.hit()) consumeMana(10.0);
        return action;
    }

    private SaveAction teleport() {
        double damageReduction = Game.calculatePlayerChance(getIntelligence() + getAgility(), getMana() >= 3.0 ? 1.0 : 0.0);
        double chance = Game.calculatePlayerChance(getAgility(), 0.95);
        SaveAction action = new SaveAction(TELEPORT, damageReduction, chance);
        if (action.successful()) consumeMana(3.0);
        return action;
    }
    
    @Override
    public CombatAction combat() {
        GameResponse response = new GameResponse("Which move do you want to use? ");
        response.setResponses(List.of(CAST_FIREBALL, SUMMON_ICE_BARRIER));

        if (getLevel() >= 3) response.addResponse(HEAL);
        if (getLevel() >= 5) response.addResponse(CAST_LIGHTNING);

        response.displayResponses("\nAvailable Moves");
        String responseValue = response.getResponse();

        switch (responseValue) {
            case CAST_FIREBALL -> {
                return castFireball();
            }
            case SUMMON_ICE_BARRIER -> {
                return summonIceBarrier();
            }
            case HEAL -> {
                return heal();
            }
            case CAST_LIGHTNING -> {
                return castLightning();
            }
            default -> throw new CombatResponseException(responseValue);
        }
    }

    @Override
    public SaveAction saveChance() {
        return teleport();
    }
}
