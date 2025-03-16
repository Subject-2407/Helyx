package com.steven.helyx.locations;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.steven.helyx.characters.Player;

public class Area {
    private String name;
    private String description;
    private List<Place> places;
    private Scanner scanner;

    public Area(String name, String description) {
        this.name = name;
        this.description = description;
        this.places = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }

    public void addPlace(Place place) {
        places.add(place);
    }

    public void explore(Player player) {
        boolean explore = true;
        while (explore && player.isAlive()) {
            player.displayInfo();
            System.out.println("==== " + name + " ====");
            System.out.println(description + "\n");

            for (int i = 0; i < places.size(); i++) {
                System.out.println("[" + (i + 1) + "] " + places.get(i).getName() + "\n  - " + places.get(i).getDescription());
            }
            System.out.println("\n[0] Exit Area");

            System.out.print("\nEnter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice > 0 && choice <= places.size()) {
                places.get(choice - 1).explore(player);
            } else if (choice == 0) {
                explore = false;
            } else {
                System.out.println("Invalid choice! Try again.");
            }
        }
    }
    
}
