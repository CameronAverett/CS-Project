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

    public GameResponse(Scanner in, String prompt, List<String> validResponses) {
        this.in = in;
        this.prompt = prompt;
        this.formattedPrompt = prompt;
        this.validResponses = validResponses;
    }

    public GameResponse(Scanner in, String prompt) {
        this(in, prompt, new ArrayList<>());
    }

    public String getResponse(boolean removeResponse) {
        while (true) {
            System.out.print(formattedPrompt);
            String response = in.next().toLowerCase();
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

    public void displayResponseData(String title, List<String> data) {
        System.out.println(title);
        for (String dataPoint : data) {
            System.out.printf("- %s%n", dataPoint);
        }
    }

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

    public boolean removeResponse(String response) {
        return validResponses.removeIf(validResponse -> response.equals(validResponse.toLowerCase()));
    }

    public boolean hasResponse(String response) {
        List<String> responses = validResponses.stream().map(String::toLowerCase).toList();
        return responses.contains(response.toLowerCase());
    }

    public String getFormattedResponse(String response) {
        for (String validResponse : validResponses) {
            if (response.equals(validResponse.toLowerCase())) {
                return validResponse;
            }
        }
        throw new GameResponseNotFoundException(validResponses, response);
    }

    public int responseSize() {
        return validResponses.size();
    }

    public void formatPrompt(Object... args) {
        this.formattedPrompt = String.format(this.prompt, args);
    }

    public static boolean getBinaryResponse(Scanner in, String prompt) {
        GameResponse response = new GameResponse(in, prompt, List.of(BINARY_T, BINARY_F));
        return response.getResponse().equals(BINARY_T);
    }
}
