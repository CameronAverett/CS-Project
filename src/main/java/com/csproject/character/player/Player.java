package com.csproject.character.player;

import com.csproject.character.Character;
import com.csproject.character.CombatAction;
import com.csproject.exceptions.game.GameResponseNotFoundException;
import com.csproject.game.Game;
import com.csproject.game.GameResponse;

import java.util.ArrayList;
import java.util.List;

public abstract class Player extends Character {

    private static final double BASE_EXP = 100.0;
    private static final double EXP_PER_LEVEL = 10.0;

    protected String name;

    private double score = 0.0;
    private double exp = 0.0;

    protected Player(String name, int level, int strength, int intelligence, int agility) {
        super(level, strength, intelligence, agility);
        this.name = name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    @Override
    public void displayStats() {
        System.out.printf(
                """
                
                ==============================
                Name: %s
                Class: %s
                Score: %.2f
                ==============================
                Lv: %d
                Exp: %.2f%%
                Hp: %s
                Mp: %s
                ==============================
                Strength: %d
                Intelligence: %d
                Agility: %d
                ==============================
                """, name, this.getClass().getSimpleName(), score, getLevel(),
                exp / getMaxExp(), getHp() + "/" + getMaxHp(), getMana() + "/" + getMaxMana(),
                getStrength(), getIntelligence(), getAgility()
        );
    }

    public String getName() {
        return name;
    }

    public double getMaxExp() {
        return BASE_EXP + (EXP_PER_LEVEL * (getLevel() - 1));
    }

    public void increaseScore(double earned) {
        this.score += earned;
    }

    public void increaseExp(double xp) {
        this.exp += xp;
        while (this.exp >= getMaxExp()) {
            this.levelUp();
            this.exp -= getMaxExp();
        }
    }

    @Override
    public void levelUp() {
        System.out.printf("%n%s has reached level %d!", name, getLevel());

        boolean selectedAttributes = false;
        while(!selectedAttributes) {
            int attributePoints = Game.getStatPoints();
            for (String attribute : CHARACTER_ATTRS) {
                GameResponse response = new GameResponse(String.format("How many attributes should be added to %s? ", attribute));
                for (int i = 0; i <= attributePoints; i++) response.addResponse(String.valueOf(i));
                response.displayResponseData("\nAvailable Stat Points", List.of(String.valueOf(attributePoints)));

                int points = Integer.parseInt(response.getResponse());
                switch (attribute) {
                    case STRENGTH -> strengthAttr.update(points);
                    case INTELLIGENCE -> intelligenceAttr.update(points);
                    case AGILITY -> agilityAttr.update(points);
                    default -> throw new GameResponseNotFoundException(response.getValidResponses(), String.valueOf(points));
                }
                attributePoints -= points;

                if (attributePoints <= 0) break;
            }

            displayStats();
            selectedAttributes = GameResponse.getBinaryResponse("Do you want to keep these stats? ");
        }

        super.levelUp();
    }
}
