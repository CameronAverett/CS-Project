package com.csproject.character.monster;

import com.csproject.character.CombatAction;
import com.csproject.character.SaveAction;
import com.csproject.game.Game;

public class Skeleton extends Monster {

    private static final double BASE_XP = 20.0;
    private static final double XP_PER_LEVEL = 9.0;

    public Skeleton(int level, int strength, int intelligence, int agility) {
        super(BASE_XP + (XP_PER_LEVEL * (level - 1)), level, strength, intelligence, agility);
    }

    // Handles combat for the Skeleton class
    @Override
    public CombatAction combat() {
        double roll = Game.getRandom().nextDouble(0.0, 1.0);
        if (roll <= 0.7) {
            double chance = Game.calculateEnemyChance(appliedStats.get(STRENGTH), 0.95);
            return new CombatAction("Slash", appliedStats.get(AGILITY) + appliedStats.get(STRENGTH), chance);
        } else {
            double chance = Game.calculateEnemyChance(appliedStats.get(STRENGTH) + appliedStats.get(AGILITY), 0.85);
            return new CombatAction("Stab", 2 * appliedStats.get(STRENGTH), chance);
        }
    }

    // Handles save chance for the Skeleton class
    @Override
    public SaveAction saveChance() {
        double damageReduction = Game.calculateEnemyChance(appliedStats.get(STRENGTH), 0.5);
        double chance = Game.calculateEnemyChance(appliedStats.get(STRENGTH) + appliedStats.get(AGILITY), 0.7);
        return new SaveAction("Shield", damageReduction, chance);
    }
}
