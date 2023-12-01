package com.csproject.character.monster;

import com.csproject.character.CombatAction;
import com.csproject.character.SaveAction;
import com.csproject.character.effects.Effect;
import com.csproject.character.effects.NoEffect;
import com.csproject.game.Game;

import java.util.Map;
import java.util.Random;

public class Slime extends Monster {

    private static final double BASE_XP = 10.0;
    private static final double XP_PER_LEVEL = 6.5;

    public Slime(int level, int strength, int intelligence, int agility) {
        super(BASE_XP + (XP_PER_LEVEL * (level - 1)), level, strength, intelligence, agility);
    }

    @Override
    public CombatAction combat() {
        double roll = Game.getRandom().nextDouble(0.0, 1.0);
        if (roll <= 0.5) {
            double chance = Game.calculateEnemyChance(appliedStats.get(STRENGTH), 0.95);
            return new CombatAction("Attack", 2 * appliedStats.get(STRENGTH), chance);
        } else {
            double chance = Game.calculateEnemyChance(appliedStats.get(STRENGTH) + appliedStats.get(AGILITY), 0.85);
            return new CombatAction("Power Attack", appliedStats.get(STRENGTH) + appliedStats.get(AGILITY), chance);
        }
    }

    @Override
    public SaveAction saveChance() {
        double damageReduction = Game.calculateEnemyChance(appliedStats.get(STRENGTH), 0.5);
        double chance = Game.calculateEnemyChance(appliedStats.get(STRENGTH) + appliedStats.get(AGILITY), 0.7);
        return new SaveAction("Harden", damageReduction, chance);
    }
}
