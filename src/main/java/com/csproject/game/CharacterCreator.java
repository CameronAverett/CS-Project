package com.csproject.game;

import com.csproject.character.Character;
import com.csproject.character.player.Player;
import com.csproject.character.player.PlayerFactory;

import java.util.*;

public class CharacterCreator {

    private final Scanner in;

    public CharacterCreator(Scanner in) {
        this.in = in;
    }

    private String getName() {
        GameResponse response = new GameResponse(in, "What is your name? ");
        return response.getResponse();
    }

    private String getPlayerClass() {
        GameResponse response = new GameResponse(in, "Please select a class: ");
        response.setResponses(List.of("Warrior", "Mage", "Archer"));

        response.displayResponses("\nAvailable Classes");
        return response.getResponse();
    }

    private HashMap<String, Integer> getStats() {
        HashMap<String, Integer> stats = new HashMap<>();

        List<String> statNames = Arrays.asList("Strength", "Intelligence", "Agility");
        int[] genStats = Character.generateStats(1, 7, statNames.size());

        boolean selectedStats = false;
        while (!selectedStats) {
            GameResponse response = new GameResponse(in, "Which stat should %d be assigned to? ", new ArrayList<>(statNames));
            response.displayResponseData("\nGenerated stats", Arrays.stream(genStats).mapToObj(String::valueOf).toList());

            for (int i = 0; i < statNames.size(); i++) {
                response.displayResponses("\nUnassigned stats");
                response.formatPrompt(genStats[i]);
                stats.put(response.getResponse(true), genStats[i]);
            }

            GameResponse exitResponse = new GameResponse(in, "Would you like to use these stats? ");
            exitResponse.setResponses(Arrays.asList("Yes", "No"));
            selectedStats = exitResponse.getResponse(false).equals("yes");
        }
        return stats;
    }


    public Player createCharacter() {
        String playerName = getName();
        String playerClass = getPlayerClass();
        HashMap<String, Integer> stats = getStats();

        return PlayerFactory.get(playerClass, playerName, 1,
                stats.get("strength"), stats.get("intelligence"), stats.get("agility"));
    }
}
