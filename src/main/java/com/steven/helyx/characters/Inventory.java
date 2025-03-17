package com.steven.helyx.characters;

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
    
    public void showMenu2(Player player) {
        final int ITEMS_PER_PAGE = 7;
         // Ensure sortedItems is updated
        int totalPages = (int) Math.ceil((double) sortedItems.size() / ITEMS_PER_PAGE);
        int currentPage = 1;
    
        while (true) {
            player.displayInfo();
            sortInventory();
            totalPages = (int) Math.ceil((double) sortedItems.size() / ITEMS_PER_PAGE);
            System.out.println("==== Inventory (Page " + currentPage + "/" + totalPages + ") ====\n");
    
            // Determine the item range for this page
            int start = (currentPage - 1) * ITEMS_PER_PAGE;
            int end = Math.min(start + ITEMS_PER_PAGE, sortedItems.size());
    
            if (sortedItems.isEmpty()) {
                System.out.println("Your inventory is empty.");
            } else {
                for (int i = start; i < end; i++) {
                    Item item = sortedItems.get(i);
                    int displayIndex = (i - start) + 1; // Reset index per page
                    String equippedIndicator = "";
                    String equipmentStats = "";
    
                    if (item instanceof Equipment) {
                        Equipment equip = (Equipment) item;
                        equipmentStats = " [ATK: " + equip.getAttackBonus() + ", DEF: " + equip.getDefenseBonus() + "]";
                        if (equip.isEquipped()) {
                            equippedIndicator = " (EQUIPPED)";
                        }
                    }
    
                    System.out.println("[" + displayIndex + "] " + item.getName() + " - " + item.getDescription() + equipmentStats + equippedIndicator);
                }
            }
    
            System.out.println();
            if (currentPage > 1) {
                System.out.println("[P] Previous Page");
            }
            if (currentPage < totalPages) {
                System.out.println("[N] Next Page");
            }
            System.out.println("[0] Return to Main Menu");
    
            System.out.print("\nEnter your choice: ");
            String input = scanner.nextLine().trim().toLowerCase();
    
            if (input.equals("0")) {
                break;
            } else if (input.equals("p") && currentPage > 1) {
                currentPage--;
            } else if (input.equals("n") && currentPage < totalPages) {
                currentPage++;
            } else {
                try {
                    int itemChoice = Integer.parseInt(input);
                    int selectedIndex = start + (itemChoice - 1);
    
                    if (selectedIndex >= start && selectedIndex < end) {
                        useItem(selectedIndex, player);
                    } else {
                        System.out.println("Invalid choice! Please pick an available item.");
                        UserInterface.enterReturn();
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input! Enter a number or use P/N for pagination.");
                    UserInterface.enterReturn();
                }
                
            }
        }
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
            System.out.println("[1] " + equipOrUse + " | [2] Remove | [3] Cancel\n");

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
                return;
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
