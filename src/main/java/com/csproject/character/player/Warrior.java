package com.csproject.character.player;

import com.csproject.character.CombatAction;
import com.csproject.character.CombatActionDisplay;
import com.csproject.character.effects.NoEffect;
import com.csproject.exceptions.character.CombatResponseException;
import com.csproject.game.Game;
import com.csproject.game.GameResponse;

import java.util.List;
import java.util.Map;

public class Warrior extends Player {

    private static final String ATTACK = "Attack";
    private static final String SHIELD = "Shield";
    private static final String RAGE = "Rage";

    public Warrior(String name, int level, int strength, int intelligence, int agility) {
        super(name, level, strength, intelligence, agility);
    }
    public CombatAction attack(Map<String, Double> appliedStats) {
        double attackDamage = appliedStats.get(STRENGTH) * 2;
        double chance = CombatAction.calculateChance(4.2, -2, appliedStats.get(STRENGTH) / getLevel(), 0.95);
        return new CombatAction(ATTACK, attackDamage, chance);
    }

    public CombatAction shieldBlock(Map<String, Double> appliedStats) {
        double blockStrength = appliedStats.get(STRENGTH) + appliedStats.get(AGILITY);
        return new CombatAction(SHIELD, blockStrength, 0.5);
    }

    public CombatAction berserkerRage(Map<String, Double> appliedStats) {
        CombatActionDisplay display = new CombatActionDisplay("\n[SELF] enters a berserker rage, gaining increased strength!",
                "\n[SELF] attempts to enter a berserker rage but fails.");
        return new CombatAction(RAGE, new NoEffect(), appliedStats.get(STRENGTH) >= 15 && getLevel() >= 10, display);
    }

    @Override
    public CombatAction combat() {
        Map<String, Double> appliedStats = applyEffects();

        GameResponse response = new GameResponse(Game.getInstance().getIn(), "Which move do you want to use? ");
        response.setResponses(List.of(ATTACK, SHIELD, RAGE));

        response.displayResponses("\nAvailable Moves");
        String responseValue = response.getResponse();

        switch (responseValue) {
            case ATTACK -> {
                return attack(appliedStats);
            }
            case SHIELD -> {
                return shieldBlock(appliedStats);
            }
            case RAGE -> {
                return berserkerRage(appliedStats);
            }
            default -> throw new CombatResponseException(responseValue);
        }
    }
}
