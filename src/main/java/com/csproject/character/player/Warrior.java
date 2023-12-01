package com.csproject.character.player;

import com.csproject.character.CombatAction;
import com.csproject.character.ActionDisplay;
import com.csproject.character.SaveAction;
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
        double attackDamage = appliedStats.get(STRENGTH) * 2;
        double chance = Game.calculatePlayerChance(appliedStats.get(STRENGTH), 0.95);
        return new CombatAction(ATTACK, attackDamage, chance);
    }

    public SaveAction shieldBlock() {
        double damageReduction = Game.calculatePlayerChance(2 * appliedStats.get(STRENGTH), 0.5);
        double chance = Game.calculatePlayerChance(appliedStats.get(STRENGTH) + appliedStats.get(ATTACK), 0.75);
        return new SaveAction(SHIELD, damageReduction, chance);
    }

    public CombatAction berserkerRage() {
        ActionDisplay display = new ActionDisplay("\n[SELF] enters a berserker rage, gaining increased strength!",
                "\n[SELF] attempts to enter a berserker rage but fails.");
        return new CombatAction(RAGE, new NoEffect(), appliedStats.get(STRENGTH) >= 15 && getLevel() >= 10, display);
    }

    @Override
    public CombatAction combat() {
        GameResponse response = new GameResponse("Which move do you want to use? ");
        response.setResponses(List.of(ATTACK, RAGE));

        response.displayResponses("\nAvailable Moves");
        String responseValue = response.getResponse();

        switch (responseValue) {
            case ATTACK -> {
                return attack();
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
