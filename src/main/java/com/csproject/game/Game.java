package com.csproject.game;

import com.csproject.character.Character;
import com.csproject.character.CombatAction;
import com.csproject.character.SaveAction;
import com.csproject.character.monster.Monster;
import com.csproject.character.monster.MonsterFactory;
import com.csproject.character.monster.Slime;
import com.csproject.character.player.Player;
import com.csproject.character.player.Warrior;
import com.csproject.exceptions.game.GameResponseNotFoundException;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Game {

    private static final String NAV_UP = "Up";
    private static final String NAV_LEFT = "Left";
    private static final String NAV_DOWN = "Down";
    private static final String NAV_RIGHT = "Right";
    private static final String NAV_EXIT = "Exit";

    private static final double CHANCE_SCALAR = 7.0;
    private static final double DEFAULT_MEAN = -2.4;
    private static final double DEFAULT_STD = 5.5;

    private static final double LEVEL_DIFFERENCE_RATE = 1.7;
    private static final double DIFFERENCE_SCALE_RATE = 3.0;

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

    private GameMap map;
    private Player player;
    private Monster enemy;

    private Game() {}

    // Method that follows the singleton class pattern
    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    // Main method of game class. Should only be called in the Main class
    public void play() {
        initialize();
        while (true) {
            if (!loop()) {
                break;
            }
        }
        quit();
    }

    // Run any processed that are required to function at the beginning of play.
    private void initialize() {
        this.player = CharacterCreator.createCharacter();
        this.map = new GameMap();
    }

    // Main gameplay loop that handles each turn the player plays
    private boolean loop() {
        boolean shouldContinue = navigation();
        if (!shouldContinue) return false;

        this.enemy = map.getCurrentRoom().getMonster();
        if (enemy == null) return true;

        String enemyName = enemy.getClass().getSimpleName();
        System.out.printf("%n%s as encountered a %s!%n", player.getName(), enemyName);

        // Handle Combat Loop
        while (!player.isDead() && !enemy.isDead()) {
            boolean shouldProceed = getPlayerAction();
            if (!shouldProceed) return false;

            combatTurn(player, enemy);
            if (enemy.isDead()) break;
            combatTurn(enemy, player);
        }
        if (player.isDead()) return false;

        double xp = calculateXp(enemy.getXp());
        System.out.printf("%n%s has defeated the %s!", player.getName(), enemyName);
        System.out.printf("%n%.2f exp has been awarded!%n", xp);
        player.increaseExp(xp);

        return true;
    }

    // Method to handle closing the game at the end of play. Displays the results of play.
    private void quit() {
        System.out.println("\nEnding Game...");
        in.close();

        System.out.printf("%nFinal Difficulty: %.2f", difficulty);
        player.displayStats();
    }

    // Handles the initial action the player makes at the start of each loop to move through the map.
    public boolean navigation() {
        map.displayMap();

        Coordinate location = map.getLocation();

        GameResponse response = new GameResponse("Where would you like to go? ");
        if (map.isRoom(new Coordinate(location.x(), location.y() - 1))) response.addResponse(NAV_UP);
        if (map.isRoom(new Coordinate(location.x() - 1, location.y()))) response.addResponse(NAV_LEFT);
        if (map.isRoom(new Coordinate(location.x(), location.y() + 1))) response.addResponse(NAV_DOWN);
        if (map.isRoom(new Coordinate(location.x() + 1, location.y()))) response.addResponse(NAV_RIGHT);
        if (map.inEntrance() || map.inExit()) response.addResponse(NAV_EXIT);
        response.addResponse(END_GAME);

        response.displayResponses("\nAvailable Actions");
        String receivedResponse = response.getResponse();

        switch (receivedResponse) {
            case (NAV_UP) -> map.setLocation(new Coordinate(location.x(), location.y() - 1));
            case (NAV_LEFT) -> map.setLocation(new Coordinate(location.x() - 1, location.y()));
            case (NAV_DOWN) -> map.setLocation(new Coordinate(location.x(), location.y() + 1));
            case (NAV_RIGHT) -> map.setLocation(new Coordinate(location.x() + 1, location.y()));
            case (NAV_EXIT) -> {
                scaleDifficulty();
                map.createGameMap();
                player.heal(player.getMaxHp());
                player.regenMana(player.getMaxMana());
            }
            case (END_GAME) -> {
                return false;
            }
            default -> throw new GameResponseNotFoundException(response.getValidResponses(), receivedResponse);
        }
        return true;
    }

    // Handles the initial action a player can make at the start of combat
    private boolean getPlayerAction() {
        boolean proceed = false;
        while (!proceed) {
            GameResponse response = new GameResponse("What would you like to do? ", ACTIONS);
            response.displayResponses("\nAvailable Responses");
            String receivedResponse = response.getResponse();

            switch (receivedResponse) {
                case DISPLAY_STATS -> player.displayStats();
                case DISPLAY_ENEMY_STATS -> enemy.displayStats();
                case PROCEED_TO_COMBAT -> proceed = true;
                case END_GAME -> {
                    return false;
                }
                default -> throw new GameResponseNotFoundException(response.getValidResponses(), receivedResponse);
            }
        }
        return true;
    }

    // Handles a single turn of combat between two characters. Should be called twice for combat, once for player and once for enemy.
    private void combatTurn(Character attacker, Character defender) {
        attacker.applyEffects();
        CombatAction action = attacker.combat();
        action.displayAction(attacker, defender);

        if (!action.hit()) return;

        double damage = action.damage();
        SaveAction save = defender.saveChance();
        if (save.successful()) {
            save.displayAction(defender, attacker);
            damage *= save.damageReduction();
        }
        defender.dealDamage(damage);
    }

    public Scanner getIn() {
        return in;
    }

    public double getDifficulty() {
        return difficulty;
    }

    public void scaleDifficulty() {
        difficulty += DIFFICULTY_RATE;
    }

    public static Random getRandom() {
        return random;
    }

    // Calculates the percentage of a normal distribution.
    private static double erf(double z) {
        double t = 1.0 / (1.0 + 0.47047 * Math.abs(z));
        double poly = t * (0.3480242 + t * (-0.0958798 + t * (0.7478556)));
        double ans = 1.0 - poly * Math.exp(-z*z);
        if (z >= 0) return  ans;
        else return -ans;
    }

    // calculates the shaded percentage of a normal distribution function
    // used to scale action chance based off some double (stat)
    private static double percentage(double std, double mean, double x) {
        double z = (x - mean) / std;
        return 0.5 * (1.0 + erf(z / (Math.sqrt(2.0))));
    }

    // need to make it based on the difference between the levels
    // https://www.desmos.com/calculator/ssejz0yc7i
    public static double calculateChance(double std, double mean, double x, double maxChance) {
        return maxChance * percentage(std, mean, x);
    }

    // Calculates the accuracy a player has to hit the enemy based on some stat value x and the maximum accuracy the player will have.
    public static double calculatePlayerChance(double x, double maxChance) {
        if (x == 0.0) return 0.0;
        int playerLevel = Game.getInstance().player.getLevel();
        int enemyLevel = Game.getInstance().enemy.getLevel();
        double z = (1 / CHANCE_SCALAR) * (((CHANCE_SCALAR * x * playerLevel) / enemyLevel) - ((enemyLevel * CHANCE_SCALAR)  / (playerLevel * x))) ;
        return calculateChance(DEFAULT_STD, DEFAULT_MEAN, z, maxChance);
    }

    // Calculates the accuracy an enemy has to hit the player based on some stat value x and the maximum accuracy the enemy will have.
    public static double calculateEnemyChance(double x, double maxChance) {
        if (x == 0.0) return 0.0;
        int playerLevel = Game.getInstance().player.getLevel();
        int enemyLevel = Game.getInstance().enemy.getLevel();
        double z = (1 / CHANCE_SCALAR) * (((CHANCE_SCALAR * x * enemyLevel) / playerLevel) - ((playerLevel * CHANCE_SCALAR) / (enemyLevel * x)));
        return calculateChance(DEFAULT_STD, DEFAULT_MEAN, z, maxChance);
    }

    // Calculates the stat points the player will have based on the difficulty and normal distribution.
    public static int getStatPoints() {
        return (int) Math.floor(LEVEL_UP_STATS * erf(1 / getInstance().difficulty));
    }

    // Method used to calculate xp earned based on the level difference between enemy and player
    // returns xp * exp([multiplier * (enemy level - player level)] / [another multiplier * enemy level])
    private double calculateXp(double xp) {
        double numerator = LEVEL_DIFFERENCE_RATE * (enemy.getLevel() - player.getLevel());
        double denominator = DIFFERENCE_SCALE_RATE * enemy.getLevel();
        return xp * Math.exp(numerator / denominator);
    }
}
