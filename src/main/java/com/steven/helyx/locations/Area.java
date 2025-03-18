package com.steven.helyx.locations;

import java.util.ArrayList;
import java.util.Scanner;

import com.steven.helyx.characters.Player;
import com.steven.helyx.locations.places.Dungeon;

public class Area {
    private String name;
    private String description;
    private ArrayList<Place> places;
    private Scanner scanner;

    public Area(String name, String description) {
        this.name = name;
        this.description = description;
        this.places = null;
        this.scanner = new Scanner(System.in);
    }

    public Area(String name, String description, ArrayList<Place> places) {
        this.name = name;
        this.description = description;
        this.places = places;
        this.scanner = new Scanner(System.in);
    }

    public String getName() { return name; }
    public String getDescription() { return description; }

    public void explore(Player player) {
        boolean explore = true;
        while (explore && player.isAlive()) {
            player.displayInfo();
            System.out.println("========== " + name + " ==========");
            System.out.println(description + "\n");

            if (places == null) {
                System.out.println("There is nothing to look out here.");
            } else {
                for (int i = 0; i < places.size(); i++) {
                    String energyRequired = "";
                    if (places.get(i) instanceof Dungeon) {
                        Dungeon dungeonPlace = (Dungeon) places.get(i);
                        energyRequired = " (" + dungeonPlace.getEnergyRequired() + " Energy per explore)";
                    }
                    System.out.println("[" + (i + 1) + "] " + places.get(i).getName() + energyRequired + "\n  - " + places.get(i).getDescription());
                }
            }

            System.out.println("\n[0] Exit Area");

            System.out.print("\nEnter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (places != null) {
                if (choice > 0 && choice <= places.size()) {
                    places.get(choice - 1).explore(player);
                } else if (choice == 0) {
                    explore = false;
                } else {
                    System.out.println("Invalid choice! Try again.");
                }
            } else explore = false;
            
        }
    }
    
}
