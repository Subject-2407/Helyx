package com.steven.helyx.game;

import com.steven.helyx.characters.*;
import com.steven.helyx.items.*;
import com.steven.helyx.utilities.UserInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Inventory {
    private List<Item> items;
    private List<Item> sortedItems;
    private Scanner scanner;

    public Inventory() {
        this.items = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }

    public void addItem(Item item) {
        items.add(item);
        sortInventory();
        System.out.println("> " + item.getName() + " has been added to your inventory.");
    }

    private void sortInventory() {
        List<Item> equipments = new ArrayList<>();
        List<Item> usables = new ArrayList<>();
    
        for (Item item : items) {
            if (item instanceof Equipment) {
                equipments.add(item);
            } else {
                usables.add(item);
            }
        }
    
        sortedItems = new ArrayList<>();
        sortedItems.addAll(equipments);
        sortedItems.addAll(usables);
    }
    

    public void showMenu(Player player) {
        boolean inInventoryMenu = true;
        while (inInventoryMenu) {
            player.displayInfo();
            showInventory();
            System.out.println("\n[0] Return to Main Menu");
            System.out.print("\nEnter your choice: ");
            int choice = scanner.nextInt() - 1;
            scanner.nextLine();

            if (choice == -1) {
                inInventoryMenu = false;
                break;
            } else {
                useItem(choice, player);
            }
        }
    }

    private void showInventory() {
        System.out.println("==== Inventory ====\n");
    
        if (items.isEmpty()) {
            System.out.println("Your inventory is empty.");
            return;
        }
    
        sortInventory();
    
        for (int i = 0; i < sortedItems.size(); i++) {
            Item item = sortedItems.get(i);
            String equippedIndicator = "";
            String equipmentStats = "";
    
            if (item instanceof Equipment) {
                Equipment equip = (Equipment) item;
                equipmentStats = " [ATK: " + equip.getAttackBonus() + ", DEF: " + equip.getDefenseBonus() + "]";
                if (equip.isEquipped()) {
                    equippedIndicator = " (EQUIPPED)";
                }
            }
    
            System.out.println("[" + (i + 1) + "] " + item.getName() + " - " + item.getDescription() + equipmentStats + equippedIndicator);
        }
    }
    

    private void useItem(int index, Player player) {
        Scanner scanner = new Scanner(System.in);
        sortInventory();

        if (index >= 0 && index < sortedItems.size()) {
            Item item = sortedItems.get(index);
            System.out.println("\n> " + item.getName());
            System.out.println("Choose an action:");
            String equipOrUse = "";
            if (item instanceof Equipment) {
                Equipment equipment = (Equipment) item;
                equipOrUse = equipment.isEquipped() ? "Unequip" : "Equip";
            } else {
                equipOrUse = "Use";
            }
            System.out.println("[1] " + equipOrUse);
            System.out.println("[2] Remove");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            if (choice == 1) {
                item.use(player);
                if (item instanceof Usable) {
                    items.remove(item);
                }
            } else if (choice == 2) {
                if (item instanceof Equipment) {
                    Equipment equipment = (Equipment) item;
                    if (equipment.isEquipped()) {
                        player.equipItem(equipment);
                    }
                }
                removeItem(index);
            } else {
                System.out.println("Invalid choice!");
            } 
        } else {
            System.out.println("Invalid choice!");
        }
        UserInterface.enterReturn();
    }

    public void removeItem(int index) {
        sortInventory();
        if (index >= 0 && index < sortedItems.size()) {
            Item item = sortedItems.get(index);
            items.remove(item);
            System.out.println("> " + item.getName() + " has been removed from your inventory.");
        } else {
            System.out.println("Invalid index! Choose a valid item number.");
        }
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }
}
