package com.steven.helyx;

import java.util.ArrayList;
import java.util.Scanner;

import com.steven.helyx.characters.*;
import com.steven.helyx.database.MonsterDatabase;
import com.steven.helyx.game.*;
import com.steven.helyx.items.*;
import com.steven.helyx.locations.Dungeon;
import com.steven.helyx.locations.Forest;
import com.steven.helyx.locations.Shop;
import com.steven.helyx.locations.Tavern;
import com.steven.helyx.utilities.UserInterface;

public class Main {
    private static String version = "v0.0.9";
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserInterface.clearConsole();
        System.out.println("==== Helyx " + version + " ====\nA simple text-based RPG game.");
        System.out.printf("\nEnter your name: ");
        String playerName = scanner.nextLine();
        Player player = new Player(playerName, "Novice");
        Inventory inventory = player.getInventory();

        System.out.println("\nGood luck out there adventurer, you will begin as a Novice class with an Iron Sword and 2 health potions in your inventory.");
        System.out.println("Remember, you only live once. If your HP reaches 0, it's game over.");
        UserInterface.enterReturn();
        
        inventory.addItem(new Equipment("Wooden Sword", "A sword made of wood.", 50, 1, 10, 5));
        inventory.addItem(new Usable("Health Potion", "Adds 50 HP.",40, 1, 50));
        inventory.addItem(new Usable("Health Potion", "Adds 50 HP.",40, 1, 50));

        // initialize items
        ArrayList<Item> shop1Items = new ArrayList<>();
        shop1Items.add(new Equipment("Iron Sword", "A sword made of iron, much better than the wooden one.", 100, 1, 20, 7));
        shop1Items.add(new Equipment("Leather Tunic", "Peasant clothes.", 30, 2, 0, 10));
        shop1Items.add(new Usable("Small Health Potion", "Restores 25 HP.",20, 1, 25));
        shop1Items.add(new Usable("Energy Potion", "Restores 5 Energy.", 30, 2, 5));
        shop1Items.add(new Usable("The Golden Potion", "Restores 50 HP and 5 Energy.", 60, 3, 50));

        // initialize world
        Tavern tavern1 = new Tavern("Elden", "The best tavern in the town. (Restores health & energy for 40 gold)", 40);
        Shop shop1 = new Shop("Yulgar", "We sell best equipments and items!", shop1Items);
        Forest forest1 = new Forest("Rivendell", "The beginner forest", 1);
        Dungeon dungeon1 = new Dungeon("Churenim", "Beginner dungeon", 2, MonsterDatabase.dungeonMonsters());

        
        while (player.isAlive()) {
            player.displayInfo();
            System.out.println("[1] Travel");
            System.out.println("[2] Inventory");
            System.out.println("[3] Stats");
            System.out.println("[4] Exit Game");
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
                        System.out.println("==== Travel ====\n");
                        System.out.println("[1] Elden Tavern");
                        System.out.println("[2] Yulgar Shop");
                        System.out.println("[3] Rivendell Forest (1 Energy)");
                        System.out.println("[4] Churenim Dungeon (2 Energy)");
                        System.out.println("\n[0] Return to Main Menu");
                        System.out.print("\nEnter your choice: ");
                        int travelChoice = scanner.nextInt();
                        scanner.nextLine();
                        System.out.println();
                        
                        switch (travelChoice) {
                            case 1:
                                tavern1.explore(player);
                                break;
                            case 2:
                                shop1.explore(player);
                                break;
                            case 3:
                                forest1.explore(player);
                                break;
                            case 4:
                                dungeon1.explore(player);
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
