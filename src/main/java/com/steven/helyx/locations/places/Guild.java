package com.steven.helyx.locations.places;

import java.util.ArrayList;
import java.util.Scanner;

import com.steven.helyx.characters.*;
import com.steven.helyx.characters.Class;
import com.steven.helyx.locations.Place;
import com.steven.helyx.utilities.UserInterface;

public class Guild extends Place{
    private Scanner scanner;
    private ArrayList<Class> classes;

    public Guild(String name, String description, ArrayList<Class> classes) {
        super(name, description);
        this.scanner = new Scanner(System.in);
        this.classes = classes;
    }

    public void explore(Player player) {
        boolean explore = true;
        while (explore && player.isAlive()) {
            player.displayInfo();
            System.out.println("========== " + name + " Guild ==========");
            System.out.println("You can choose your new class here.\n");

            for (int i = 0; i < classes.size(); i++) {
                Class classItem = classes.get(i);
                System.out.println("[" + (i + 1) + "] " + classItem.getName() + " (" + classItem.getPrice() + " Gold)\n  - " + classItem.getDescription());
            }

            System.out.println("\n[0] Exit");

            System.out.print("\nEnter your choice: ");
            int choice = scanner.nextInt();

            if (choice > 0 && choice <= classes.size()) {
                chooseClass(player, classes.get(choice - 1));
            } else if (choice == 0) {
                explore = false;
            } else {
                System.out.println("Invalid choice!");
                UserInterface.enterReturn();
            }
        }
    }

    private void chooseClass(Player player, Class chosenClass) {
        int price = chosenClass.getPrice();
        if (player.getGolds() >= price) {
            if (player.getCurrentClass() != chosenClass) {
                player.takeGold(price);
                player.changeClass(chosenClass);
            } else {
                System.out.println("> You've chosen this class already!");
            }
        } else {
            System.out.println("> You don't have enough gold to choose this class!");
        }
        UserInterface.enterReturn();
    }
}
