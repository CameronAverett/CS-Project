package com.csproject.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GameResponse {

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
            if (validResponses.isEmpty() || hasResponse(response)) {
                if (removeResponse) removeResponse(response);
                return response;
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

    public int responseSize() {
        return validResponses.size();
    }

    public void formatPrompt(Object... args) {
        this.formattedPrompt = String.format(this.prompt, args);
    }
}
