package com.csproject.game;

import com.csproject.character.player.Player;
import com.csproject.character.player.PlayerFactory;
import com.csproject.util.ScannerUtil;

import java.util.*;

public class CharacterCreator {

    private final Random random = new Random();

    private final Scanner in;

    public CharacterCreator(Scanner in) {
        this.in = in;
    }

    private String getName() {
        System.out.print("What is your name? ");
        return in.next();
    }

    private String getPlayerClass() {
        List<String> availableClasses = List.of("default");
        System.out.println("\nAvailable Classes");
        for (String availableClass : availableClasses) {
            System.out.printf("- %s%n", availableClass);
        }
        return ScannerUtil.getResponse(in, "Please select a class: ", availableClasses);
    }

    private HashMap<String, Integer> getStats() {
        List<String> statNames = Arrays.asList("strength", "intelligence", "agility");
        HashMap<String, Integer> stats = new HashMap<>();

        int[] genStats = new int[statNames.size()];
        for(int i = 0; i < genStats.length; i++) {
            genStats[i] = random.nextInt(1, 7);
        }

        boolean selectedStats = false;
        while (!selectedStats) {
            System.out.println("\nGenerated stats");
            for (int stat : genStats) {
                System.out.printf("- %d%n", stat);
            }

            List<String> assignedStats = new ArrayList<>();
            for (int i = 0; i < statNames.size(); i++) {
                System.out.println("\nUnassigned stats");
                List<String> statNameCopy = new ArrayList<>(statNames);
                statNameCopy.removeAll(assignedStats);
                for (String stat : statNameCopy) {
                    System.out.printf("- %s%n", stat);
                }
                String prompt = String.format("Which stat should %d be assigned to? ", genStats[i]);
                String response = ScannerUtil.getResponse(in, prompt, statNameCopy);
                stats.put(response, genStats[i]);
                assignedStats.add(response);
            }

            String prompt = "Would you like to use these stats? ";
            selectedStats = ScannerUtil.getResponse(in, prompt, List.of("yes", "no")).equals("yes");
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
