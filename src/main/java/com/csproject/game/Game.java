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

import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Game {

    private static final double CHANCE_SCALAR = 7.0;
    private static final double DEFAULT_MEAN = -2.4;
    private static final double DEFAULT_STD = 5.5;

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
    private Monster enemy;

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
        this.enemy = MonsterFactory.get("Slime", 1, 4, 2, 6);

        while (!player.isDead() && !enemy.isDead()) {
            boolean shouldProceed = getPlayerAction();
            if (!shouldProceed) return false;

            combatTurn(player, enemy);
            if (enemy.isDead()) break;
            combatTurn(enemy, player);
        }

        if (player.isDead()) {
            return false;
        }

        player.increaseExp(enemy.getXp());
        return true;
    }

    private void quit() {
        System.out.println("\nEnding Game...");
        in.close();

        System.out.printf("%nFinal Difficulty: %.2f", difficulty);
        player.displayStats();
    }

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

    private void combatTurn(Character attacker, Character defender) {
        attacker.applyEffects();
        CombatAction action = attacker.combat();
        action.displayAction(attacker, defender);

        double damage = action.damage();
        SaveAction save = defender.saveChance();
        if (save.successful()) {
            save.displayAction(attacker, defender);
            damage *= save.damageReduction();
        }

        if (action.hit()) defender.dealDamage(damage);
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
    // used to scale action chance based off some double (stat)
    public static double percentage(double std, double mean, double x) {
        double z = (x - mean) / std;
        return 0.5 * (1.0 + erf(z / (Math.sqrt(2.0))));
    }

    // need to make it based on the difference between the levels
    // https://www.desmos.com/calculator/ssejz0yc7i
    public static double calculateChance(double std, double mean, double x, double maxChance) {
        return maxChance * percentage(std, mean, x);
    }

    public static double calculatePlayerChance(double x, double maxChance) {
        int playerLevel = Game.getInstance().player.getLevel();
        int enemyLevel = Game.getInstance().enemy.getLevel();
        double z = (1 / CHANCE_SCALAR) * (((CHANCE_SCALAR * x * playerLevel) / enemyLevel) - ((enemyLevel * CHANCE_SCALAR)  / (playerLevel * x))) ;
        return calculateChance(DEFAULT_STD, DEFAULT_MEAN, z, maxChance);
    }

    public static double calculateEnemyChance(double x, double maxChance) {
        int playerLevel = Game.getInstance().player.getLevel();
        int enemyLevel = Game.getInstance().enemy.getLevel();
        double z = (1 / CHANCE_SCALAR) * (((CHANCE_SCALAR * x * enemyLevel) / playerLevel) - ((playerLevel * CHANCE_SCALAR) / (enemyLevel * x)));
        return calculateChance(DEFAULT_STD, DEFAULT_MEAN, z, maxChance);
    }

    public static int getStatPoints() {
        return (int) Math.floor(LEVEL_UP_STATS * erf(1 / getInstance().difficulty));
    }
}
