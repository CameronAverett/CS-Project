package com.csproject.game;

import com.csproject.character.CombatAction;
import com.csproject.character.monster.Monster;
import com.csproject.character.monster.MonsterFactory;
import com.csproject.character.player.Player;
import com.csproject.exceptions.game.GameResponseNotFoundException;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Game {

    private static final double DIFFICULTY_RATE = 0.1;
    private static final double LEVEL_UP_STATS = 10;

    private static final String DISPLAY_STATS = "Display Stats";
    private static final String DISPLAY_ENEMY_STATS = "Display Enemy";
    private static final String PROCEED_TO_COMBAT = "Proceed";
    private static final String END_GAME = "Quit";

    private static final List<String> ACTIONS = List.of(DISPLAY_STATS, DISPLAY_ENEMY_STATS, PROCEED_TO_COMBAT, END_GAME);

    private static final Random random = new Random();

    private static Game instance;

    private final Scanner in = new Scanner(System.in);
    private double difficulty = 1.0;

    private Player player;

    private Game() {}

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
        quit();
    }

    private void initialize() {
        this.player = CharacterCreator.createCharacter();
    }

    private boolean loop() {
        Monster monster = MonsterFactory.get("Slime", 1, 4, 2, 6);

        while (!player.isDead() && !monster.isDead()) {
            boolean proceed = false;
            while (!proceed) {
                GameResponse response = new GameResponse("What would you like to do? ", ACTIONS);
                response.displayResponses("\nAvailable Responses");
                String receivedResponse = response.getResponse();

                switch (receivedResponse) {
                    case DISPLAY_STATS -> player.displayStats();
                    case DISPLAY_ENEMY_STATS -> monster.displayStats();
                    case PROCEED_TO_COMBAT -> proceed = true;
                    case END_GAME -> {
                        return false;
                    }
                    default -> throw new GameResponseNotFoundException(response.getValidResponses(), receivedResponse);
                }
            }

            CombatAction playerAction = player.combat();
            playerAction.displayAction(player, monster);

            if (playerAction.hit()) monster.dealDamage(playerAction.damage());
        }

        if (player.isDead()) {
            return false;
        }

        player.increaseExp(monster.getXp());
        return true;
    }

    private void quit() {
        System.out.println("\nEnding Game...");
        in.close();

        System.out.printf("%nFinal Difficulty: %.2f", difficulty);
        player.displayStats();
    }

    public Scanner getIn() {
        return in;
    }

    public int getPlayerLevel() {
        return player.getLevel();
    }

    public static Random getRandom() {
        return random;
    }

    public static double erf(double z) {
        double t = 1.0 / (1.0 + 0.47047 * Math.abs(z));
        double poly = t * (0.3480242 + t * (-0.0958798 + t * (0.7478556)));
        double ans = 1.0 - poly * Math.exp(-z*z);
        if (z >= 0) return  ans;
        else return -ans;
    }

    // calculates the shaded percentage of a normal distribution function
    // used to scale attack chance based off some double (stat)
    public static double percentage(double std, double mean, double x) {
        double z = (x - mean) / std;
        return 0.5 * (1.0 + erf(z / (Math.sqrt(2.0))));
    }

    // need to make it based on the difference between the levels
    // https://www.desmos.com/calculator/ssejz0yc7i
    public static double calculateChance(double std, double mean, double x, double maxChance) {
        return maxChance * percentage(std, mean, x);
    }

    public static int getStatPoints() {
        return (int) Math.floor(LEVEL_UP_STATS * erf(1 / getInstance().difficulty));
    }
}
