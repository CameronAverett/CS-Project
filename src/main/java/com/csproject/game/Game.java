package com.csproject.game;

import com.csproject.character.player.Player;
import com.csproject.character.player.PlayerFactory;

import java.util.Random;
import java.util.Scanner;

public class Game {

    private static Game instance = null;

    private Scanner in = new Scanner(System.in);
    private double difficulty = 1.0;

    private Player player;

    private Game() {
        this.player = createPlayer();
    }

    public static Game getInstance() {
        if (Game.instance == null) {
            Game.instance = new Game();
        }
        return Game.instance;
    }

    public Player createPlayer() {
        System.out.println("What is your name? ");
        String playerName = in.next();

        System.out.println(
                """
                Available Classes
                - Default
                """
        );
        System.out.println("Please select a class: ");
        String playerClass = in.next();

        Random random = new Random();
        int rand1 = random.nextInt(1, 7);
        int rand2 = random.nextInt(1, 7);
        int rand3 = random.nextInt(1, 7);

        boolean selectedStats = false;
        while (!selectedStats) {
            System.out.println(String.format(
                    """
                    Randomly Rolled Stats
                    1. %d
                    2. %d
                    3. %d
                    """,
                    rand1, rand2, rand3
            ));
            selectedStats = true;
        }

        return PlayerFactory.get(playerClass, playerName, 1, rand1, rand2, rand3);
    }

    public void play() {
        System.out.println("Hello, World!");
    }

    public double getDifficulty() {
        return this.difficulty;
    }
}
