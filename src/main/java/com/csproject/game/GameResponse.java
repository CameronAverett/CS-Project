package com.csproject.game;

import com.csproject.exceptions.game.GameResponseNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GameResponse {

    private static final String BINARY_T = "Yes";
    private static final String BINARY_F = "No";

    private final Scanner in;
    private final String prompt;

    private String formattedPrompt;
    private List<String> validResponses;

    public GameResponse(String prompt, List<String> validResponses) {
        this.in = Game.getInstance().getIn();
        this.prompt = prompt;
        this.formattedPrompt = prompt;
        this.validResponses = validResponses;
    }

    public GameResponse(String prompt) {
        this(prompt, new ArrayList<>());
    }

    // Get the response from the user. If responses should be removed, it removes them from the validResponses list.
    public String getResponse(boolean removeResponse) {
        while (true) {
            System.out.print(formattedPrompt);
            String response = in.nextLine().toLowerCase();
            if (validResponses.isEmpty()) {
                return response;
            } else if (hasResponse(response)) {
                String format = getFormattedResponse(response);
                if (removeResponse) removeResponse(response.toLowerCase());
                return format;
            }
        }
    }

    public String getResponse() {
        return this.getResponse(false);
    }

    // Displays data necessary for response but not stored in gameResponse class.
    public void displayResponseData(String title, List<String> data) {
        System.out.println(title);
        for (String dataPoint : data) {
            System.out.printf("- %s%n", dataPoint);
        }
    }

    // Displays all the valid responses under a given title.
    public void displayResponses(String title) {
        System.out.println(title);
        for (String response : validResponses) {
            System.out.printf("- %s%n", response);
        }
    }

    public void displayResponses() {
        this.displayResponses("Valid Responses");
    }

    public void setResponses(List<String> validResponses) {
        this.validResponses = validResponses;
    }

    public void addResponse(String response) {
        validResponses.add(response);
    }

    // Method to remove a given response from the validResponses list.
    public boolean removeResponse(String response) {
        return validResponses.removeIf(validResponse -> response.equals(validResponse.toLowerCase()));
    }

    // Method to determine if a given response is in the validResponses list.
    public boolean hasResponse(String response) {
        List<String> responses = validResponses.stream().map(String::toLowerCase).toList();
        return responses.contains(response.toLowerCase());
    }

    // Method to format response to the same format as the corresponded valid response.
    public String getFormattedResponse(String response) {
        for (String validResponse : validResponses) {
            if (response.equals(validResponse.toLowerCase())) {
                return validResponse;
            }
        }
        throw new GameResponseNotFoundException(validResponses, response);
    }

    public List<String> getValidResponses() {
        return validResponses;
    }

    // Format the prompt with the specified arguments. Should only be used if the prompt contains the correct placeholder value.
    public void formatPrompt(Object... args) {
        this.formattedPrompt = String.format(this.prompt, args);
    }

    // Returns the response of a yes or no prompted response
    public static boolean getBinaryResponse(String prompt) {
        GameResponse response = new GameResponse(prompt, List.of(BINARY_T, BINARY_F));
        return response.getResponse().equals(BINARY_T);
    }
}
