package com.steven.helyx;

import java.util.ArrayList;
import java.util.Scanner;

import com.steven.helyx.characters.*;
import com.steven.helyx.characters.Class;
import com.steven.helyx.database.AreaDatabase;
import com.steven.helyx.game.*;
import com.steven.helyx.items.*;
import com.steven.helyx.locations.Area;
import com.steven.helyx.utilities.UserInterface;

public class Main {
    private static String version = "v0.1.2 alpha";
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserInterface.clearConsole();
        System.out.println("==== Helyx " + version + " ====\nA simple turn-based RPG game.");
        System.out.printf("\nEnter your name: ");
        String playerName = scanner.nextLine();
        Player player = new Player(playerName, new Class("Novice", "Where everything begins.", 0, 0, 0, 0));
        Inventory inventory = player.getInventory();

        System.out.println("\nGood luck out there adventurer, you will begin as a Novice class with an Iron Sword and 2 health potions in your inventory.");
        System.out.println("Remember, you only live once. If your HP reaches 0, it's game over.");
        UserInterface.enterReturn();
        
        inventory.addItem(new Equipment("Wooden Sword", "A sword made of wood.", 50, 1, 10, 5));
        inventory.addItem(new Usable("Health Potion", "Adds 50 HP.",40, 1, 50));
        inventory.addItem(new Usable("Health Potion", "Adds 50 HP.",40, 1, 50));

        player.gainGold(10000);

        // render area
        ArrayList<Area> beginnerAreas = AreaDatabase.beginnerAreas();

        while (player.isAlive()) {
            player.displayInfo();
            System.out.println("[1] Travel");
            System.out.println("[2] Inventory");
            System.out.println("[3] Stats");
            System.out.println("[4] Class Info");
            System.out.println("[5] Exit Game");
            System.out.print("\nEnter your choice: ");
            int mainChoice = scanner.nextInt();
            scanner.nextLine();
            
            switch (mainChoice) {
                case 2:
                    inventory.showMenu2(player);
                    break;
                case 1:
                    travelMenu(beginnerAreas, player, scanner);
                    break;
                case 3:
                    player.displayStatsMenu(scanner);
                    break;
                case 4:
                    Class.classMenu(player);
                    break;
                case 5:
                    System.out.println("Exiting the game...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice, try again.");
            }
        }
        
        System.out.println("Game Over..");
        scanner.close();
    }

    private static void travelMenu(ArrayList<Area> areas, Player player, Scanner scanner) {
        boolean inTravelMenu = true;
        while (inTravelMenu) {
            player.displayInfo();
            if (!player.isAlive()) { break; }
            System.out.println("==== Travel to Area ====\n");

            for (int i = 0; i < areas.size(); i++) {
                Area area = areas.get(i);
                System.out.println("[" + (i + 1) + "] " + area.getName());
            }
            System.out.println("\n[0] Return to Main Menu");
            System.out.print("\nEnter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            System.out.println();
            
            if (choice > 0 && choice <= areas.size()) {
                areas.get(choice - 1).explore(player);
            } else if (choice == 0) {
                inTravelMenu = false;
            } else {
                System.out.println("Invalid choice!");
                UserInterface.enterReturn();
            }
        }
    }
}
