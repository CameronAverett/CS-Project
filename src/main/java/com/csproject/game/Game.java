package com.csproject.game;

public class Game {

    private static Game instance = null;

    private double difficulty;

    private Game() {
        this.difficulty = 1.0;
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
