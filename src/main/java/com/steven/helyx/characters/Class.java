package com.steven.helyx.characters;

import java.util.Scanner;

import com.steven.helyx.utilities.UserInterface;

public class Class {
    private String name;
    private String description;
    private int price;
    private int strengthBonus;
    private int defenseBonus;
    private int dexterityBonus;

    public Class(String name, String description, int price, int strengthBonus, int defenseBonus, int dexterityBonus) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.strengthBonus = strengthBonus;
        this.defenseBonus = defenseBonus;
        this.dexterityBonus = dexterityBonus;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getPrice() { return price;}
    public int getStrengthBonus() { return strengthBonus; }
    public int getDefenseBonus() { return defenseBonus; }
    public int getDexterityBonus() { return dexterityBonus; }

    public static void classMenu(Player player) {
        Scanner scanner = new Scanner(System.in);
        Class playerClass = player.getCurrentClass();
        boolean inClassMenu = true;
        while (inClassMenu) {
            player.displayInfo();
            System.out.println("==== " + playerClass.getName() + " Class ====");
            System.out.println(playerClass.getDescription() + "\n");
            System.out.println("Strength: +" + playerClass.getStrengthBonus());
            System.out.println("Defense: +" + playerClass.getDefenseBonus());
            System.out.println("Dexterity: +" + playerClass.getDexterityBonus());
            System.out.println("\n[0] Return to Main Menu\n");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            if (choice == 0) {
                inClassMenu = false;
            } else {
                System.out.println("Invalid choice!");
                UserInterface.enterReturn();
            }
        }
    }
}
