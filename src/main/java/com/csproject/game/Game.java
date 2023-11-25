package com.csproject.game;

import com.csproject.character.player.Player;
import java.util.Scanner;

public class Game {

    private static Game instance = null;

    private Scanner in = new Scanner(System.in);
    private double difficulty = 1.0;

    private final Player player;

    private Game() {
        this.player = new CharacterCreator(in).createCharacter();
    }

    public static Game getInstance() {
        if (Game.instance == null) {
            Game.instance = new Game();
        }
        return Game.instance;
    }

    public void play() {
        System.out.println("Hello, World!");
    }

    public double getDifficulty() {
        return this.difficulty;
    }
}
