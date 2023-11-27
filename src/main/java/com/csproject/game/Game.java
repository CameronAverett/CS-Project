package com.csproject.game;

import com.csproject.character.CombatAction;
import com.csproject.character.monster.Slime;
import com.csproject.character.player.Player;

import java.util.Random;
import java.util.Scanner;

public class Game {

    private static final double DIFFICULTY_RATE = 0.1;

    private static final Random random = new Random();

    private static Game instance;

    private final Scanner in = new Scanner(System.in);
    private double difficulty = 1.0;

    private Player player;

    private Game() {

    }

    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    public void play() {
        initialize();

        while (true) {
            if (!loop()) {
                break;
            }
        }
    }

    private void initialize() {
        this.player = new CharacterCreator(in).createCharacter();
    }

    private boolean loop() {
        Slime slime = new Slime(1, 5, 6, 1);

        while (!slime.isDead()) {
            CombatAction playerAction = this.player.combat();
            playerAction.displayAction(this.player, slime);

            if (playerAction.hit()) {
                slime.dealDamage(playerAction.damage());
            }
        }

        slime.displayStats();

        return false;
    }

    public Scanner getIn() {
        return in;
    }

    public double getDifficulty() {
        return this.difficulty;
    }

    private void scaleDifficulty() {
        this.difficulty += DIFFICULTY_RATE;
    }

    public int getPlayerLevel() {
        return player.getLevel();
    }

    public static Random getRandom() {
        return random;
    }
}
