package com.steven.helyx;

import java.util.ArrayList;
import java.util.Scanner;

import com.steven.helyx.characters.*;
import com.steven.helyx.characters.Class;
import com.steven.helyx.database.AreaDatabase;
import com.steven.helyx.database.ClassDatabase;
import com.steven.helyx.game.*;
import com.steven.helyx.items.*;
import com.steven.helyx.locations.Area;
import com.steven.helyx.utilities.UserInterface;

public class Main {
    private static String version = "v0.4.0 alpha";
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserInterface.clearConsole();
        System.out.println("========== Helyx " + version + " ==========");
        System.out.println("     A simple turn-based RPG game.");
        System.out.println("========================================");
        System.out.printf("\n> Enter your name: ");
        String playerName = scanner.nextLine();
        Player player = new Player(playerName, ClassDatabase.noviceClass());
        Inventory inventory = player.getInventory();

        System.out.println("\nSteel your resolve, adventurer.");
        System.out.println("You begin your journey as a mere Novice, armed with nothing but a Wooden Sword, a worn Leather Tunic, and two fragile health potions.");
        System.out.println("Tread carefully, death is permanent. If your HP hits zero, your story ends here..");
        UserInterface.enterReturn();
        
        Equipment woodenSword = new Equipment("Wooden Sword", "A sword made of wood.", 50, 1, 10, 5);
        Equipment leatherArmor = new Equipment("Leather Tunic", playerName, 0, 2, 0, 5);
        inventory.addItem(woodenSword);
        inventory.addItem(leatherArmor);
        inventory.addItem(new Usable("Health Potion", "Adds 50 HP.",40, 1, 50));
        inventory.addItem(new Usable("Health Potion", "Adds 50 HP.",40, 1, 50));
        player.equipItem(woodenSword);
        player.equipItem(leatherArmor);

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
            System.out.println("========== Travel to Area ==========\n");

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
