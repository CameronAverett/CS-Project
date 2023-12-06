package com.csproject.game;

import com.csproject.character.Character;
import com.csproject.character.player.Player;
import com.csproject.character.player.PlayerFactory;

import java.util.*;

public class CharacterCreator {

    private CharacterCreator() {}

    // Method to get the name from the user
    private static String getName() {
        GameResponse response = new GameResponse("What is your name? ");
        return response.getResponse();
    }

    // Method to get the class from the user, will run until a valid response is given
    private static String getPlayerClass() {
        GameResponse response = new GameResponse("Please select a class: ");
        response.setResponses(PlayerFactory.PLAYER_CLASSES);

        response.displayResponses("\nAvailable Classes");
        return response.getResponse();
    }

    // Get stats from player and repeat process if the user doesn't want to keep those allotted stats. Generated stat points are not regenerated
    private static HashMap<String, Integer> getStats() {
        HashMap<String, Integer> stats = new HashMap<>();
        
        int[] genStats = Character.generateStats(1, 7, Character.CHARACTER_ATTRS.size());

        boolean selectedStats = false;
        while (!selectedStats) {
            GameResponse response = new GameResponse("Which stat should %d be assigned to? ", new ArrayList<>(Character.CHARACTER_ATTRS));
            response.displayResponseData("\nGenerated stats", Arrays.stream(genStats).mapToObj(String::valueOf).toList());

            for (int i = 0; i < Character.CHARACTER_ATTRS.size(); i++) {
                response.displayResponses("\nUnassigned Stats");
                response.formatPrompt(genStats[i]);
                stats.put(response.getResponse(true), genStats[i]);
            }

            selectedStats = GameResponse.getBinaryResponse("Would you like to use these stats? ");
        }
        return stats;
    }

    // Method that runs the character creator process
    public static Player createCharacter() {
        String playerName = getName();
        String playerClass = getPlayerClass();
        HashMap<String, Integer> stats = getStats();

        return PlayerFactory.get(playerClass, playerName, 1,
                stats.get(Character.STRENGTH), stats.get(Character.INTELLIGENCE), stats.get(Character.AGILITY));
    }
}
