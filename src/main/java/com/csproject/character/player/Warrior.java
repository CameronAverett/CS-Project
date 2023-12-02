package com.csproject.character.player;

import com.csproject.character.CombatAction;
import com.csproject.character.ActionDisplay;
import com.csproject.character.SaveAction;
import com.csproject.character.effects.IncreaseStrength;
import com.csproject.exceptions.character.CombatResponseException;
import com.csproject.game.Game;
import com.csproject.game.GameResponse;

import java.util.List;

public class Warrior extends Player {

    private static final String ATTACK = "Attack";
    private static final String HEAL = "Heal";
    private static final String SMITE = "Smite";
    private static final String RAGE = "Rage";
    private static final String SHIELD = "Shield";

    public Warrior(String name, int level, int strength, int intelligence, int agility) {
        super(name, level, strength, intelligence, agility);
    }

    private CombatAction attack() {
        double attackDamage = 2.0 * getStrength();
        double chance = Game.calculatePlayerChance(getStrength(), 0.95);
        return new CombatAction(ATTACK, attackDamage, chance);
    }

    private CombatAction heal() {
        boolean canUse = getIntelligence() >= 8 && getMana() >= 10.0;
        if (canUse) consumeMana(10.0);
        return new CombatAction(HEAL, canUse);
    }

    private CombatAction smite() {
        boolean canUse = getStrength() >= 12 && getMana() >= 12.5;
        if (canUse) consumeMana(12.5);
        return new CombatAction(SMITE, 3.0 * getStrength(), canUse);
    }

    private CombatAction berserkerRage() {
        ActionDisplay display = new ActionDisplay("\n[SELF] enters a berserker rage, gaining increased strength!",
                "\n[SELF] attempts to enter a berserker rage but fails.");
        return new CombatAction(RAGE, new IncreaseStrength(5, 2.0), getStrength() >= 15, display);
    }

    private SaveAction shieldBlock() {
        double damageReduction = Game.calculatePlayerChance(2.0 * getStrength(), 0.5);
        double chance = Game.calculatePlayerChance(getStrength() + getAgility(), 0.75);
        return new SaveAction(SHIELD, damageReduction, chance);
    }

    @Override
    public CombatAction combat() {
        GameResponse response = new GameResponse("Which move do you want to use? ");
        response.setResponses(List.of(ATTACK));

        if (getLevel() >= 3) response.addResponse(HEAL);
        if (getLevel() >= 5) response.addResponse(SMITE);
        if (getLevel() >= 10) response.addResponse(RAGE);

        response.displayResponses("\nAvailable Moves");
        String responseValue = response.getResponse();

        switch (responseValue) {
            case ATTACK -> {
                return attack();
            }
            case HEAL -> {
                return heal();
            }
            case SMITE -> {
                return smite();
            }
            case RAGE -> {
                return berserkerRage();
            }
            default -> throw new CombatResponseException(responseValue);
        }
    }

    @Override
    public SaveAction saveChance() {
        return shieldBlock();
    }
}
