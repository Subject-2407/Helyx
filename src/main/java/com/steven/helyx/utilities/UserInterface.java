package com.steven.helyx.utilities;

import java.util.Scanner;

public class UserInterface {
    public static void enterReturn() {
        System.out.print("\nPress Enter to return...");
        new Scanner(System.in).nextLine();
    }

    public static void clearConsole() {
        try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            System.out.println("Error clearing console.");
        }
    }
}
