package com.csproject.util;

import java.util.List;
import java.util.Scanner;

public class ScannerUtil {
    public static String getResponse(Scanner in, String prompt, List<String> validResponses) {
        while (true) {
            System.out.print(prompt);
            String response = in.next().toLowerCase();
            if (validResponses.contains(response)) {
                return response;
            }
        }
    }
}
