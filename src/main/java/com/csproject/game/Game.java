package com.csproject.game;

import com.csproject.character.monster.Slime;
import com.csproject.character.player.Player;

import java.util.Random;
import java.util.Scanner;

public class Game {

    private static final Random random = new Random();

    private static Game instance;

    private Scanner in = new Scanner(System.in);
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
        loop();
    }

    private void initialize() {
        this.player = new CharacterCreator(in).createCharacter();
    }

    private void loop() {
        player.displayStats();

        new Slime(15.2, 1, 5, 6, 1).displayStats();
    }

    public double getDifficulty() {
        return this.difficulty;
    }

    public static Random getRandom() {
        return random;
    }
}
