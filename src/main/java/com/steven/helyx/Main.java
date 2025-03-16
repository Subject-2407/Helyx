package com.steven.helyx;

import java.util.ArrayList;
import java.util.Scanner;

import com.steven.helyx.characters.*;
import com.steven.helyx.characters.Class;
import com.steven.helyx.database.ClassDatabase;
import com.steven.helyx.database.ItemDatabase;
import com.steven.helyx.database.MonsterDatabase;
import com.steven.helyx.game.*;
import com.steven.helyx.items.*;
import com.steven.helyx.locations.Area;
import com.steven.helyx.locations.Dungeon;
import com.steven.helyx.locations.Forest;
import com.steven.helyx.locations.Guild;
import com.steven.helyx.locations.Shop;
import com.steven.helyx.locations.Tavern;
import com.steven.helyx.utilities.UserInterface;

public class Main {
    private static String version = "v0.1.0 alpha";
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserInterface.clearConsole();
        System.out.println("==== Helyx " + version + " ====\nA simple text-based RPG game.");
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

        // render area
        Area eldoria = new Area("Eldoria Plains", "Vast golden fields with a gentle breeze.");

        // render places
        eldoria.addPlace(new Tavern("Moonlit Ember", "A warm and elegant inn. (Restore health & energy for 40 gold)", 40));
        eldoria.addPlace(new Shop("Emberforge", "We sell best quality equipments and items! (Shop)", ItemDatabase.emberforgeItems()));
        eldoria.addPlace(new Guild("Stormbringers", "A guild known for its powerful warriors who bring chaos to battle. (Guild)", ClassDatabase.beginnerClasses()));
        eldoria.addPlace(new Forest("Eldertree", "Home to a massive ancient tree said to contain untold wisdom. (Forest)", 1));
        eldoria.addPlace(new Dungeon("Churenim Cavern", "A deep cavern where the cries of the lost echo eternally. (Dungeon)", 2, MonsterDatabase.dungeonMonsters()));

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
                    inventory.showMenu(player);
                    break;
                case 1:
                    boolean inTravelMenu = true;
                    while (inTravelMenu) {
                        player.displayInfo();
                        if (!player.isAlive()) { break; }
                        System.out.println("==== Travel to Area ====\n");
                        System.out.println("[1] Eldoria Plains");
                        System.out.println("[2] Unknown Area");
                        System.out.println("[3] Unknown Area");
                        System.out.println("\n[0] Return to Main Menu");
                        System.out.print("\nEnter your choice: ");
                        int choice = scanner.nextInt();
                        scanner.nextLine();
                        System.out.println();
                        
                        switch (choice) {
                            case 1:
                                eldoria.explore(player);
                                break;
                            case 0:
                                inTravelMenu = false;
                                break;
                            default:
                                System.out.println("Invalid choice!");
                                UserInterface.enterReturn();
                        }
                    }
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
}
