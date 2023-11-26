package com.csproject.game;

import com.csproject.character.player.Player;

import java.util.Random;
import java.util.Scanner;

public class Game {

    private static final Random random = new Random();

    private static Game instance;

    private Scanner in = new Scanner(System.in);
    private double difficulty = 1.0;

    private final Player player;

    private Game() {
        this.player = new CharacterCreator(in).createCharacter();
    }

    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    public void play() {
        System.out.println("Hello, World!");
    }

    public double getDifficulty() {
        return this.difficulty;
    }

    public static Random getRandom() {
        return random;
    }
}
