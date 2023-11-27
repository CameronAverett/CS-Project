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
        response.setResponses(PlayerFactory.PLAYER_CLASSES);

        response.displayResponses("\nAvailable Classes");
        return response.getResponse();
    }

    private HashMap<String, Integer> getStats() {
        HashMap<String, Integer> stats = new HashMap<>();
        
        int[] genStats = Character.generateStats(1, 7, Character.CHARACTER_ATTRS.size());

        boolean selectedStats = false;
        while (!selectedStats) {
            GameResponse response = new GameResponse(in, "Which stat should %d be assigned to? ", new ArrayList<>(Character.CHARACTER_ATTRS));
            response.displayResponseData("\nGenerated stats", Arrays.stream(genStats).mapToObj(String::valueOf).toList());

            for (int i = 0; i < Character.CHARACTER_ATTRS.size(); i++) {
                response.displayResponses("\nUnassigned stats");
                response.formatPrompt(genStats[i]);
                stats.put(response.getResponse(true), genStats[i]);
            }

            selectedStats = GameResponse.getBinaryResponse(in, "Would you like to use these stats? ");
        }
        return stats;
    }


    public Player createCharacter() {
        String playerName = getName();
        String playerClass = getPlayerClass();
        HashMap<String, Integer> stats = getStats();

        return PlayerFactory.get(playerClass, playerName, 1,
                stats.get(Character.STRENGTH), stats.get(Character.INTELLIGENCE), stats.get(Character.AGILITY));
    }
}
