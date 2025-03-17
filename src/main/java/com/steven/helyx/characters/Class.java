package com.steven.helyx.characters;

import java.util.ArrayList;
import java.util.Scanner;

import com.steven.helyx.characters.skills.Skill;
import com.steven.helyx.utilities.UserInterface;

public class Class {
    private String name;
    private String description;
    private int price;
    private int strengthBonus;
    private int defenseBonus;
    private int dexterityBonus;
    private ArrayList<Skill> additionalSkills;

    public Class(String name, String description, int price, int strengthBonus, int defenseBonus, int dexterityBonus) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.strengthBonus = strengthBonus;
        this.defenseBonus = defenseBonus;
        this.dexterityBonus = dexterityBonus;
    }

    public Class(String name, String description, int price, int strengthBonus, int defenseBonus, int dexterityBonus, ArrayList<Skill> additionalSkills) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.strengthBonus = strengthBonus;
        this.defenseBonus = defenseBonus;
        this.dexterityBonus = dexterityBonus;
        this.additionalSkills = additionalSkills;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getPrice() { return price;}
    public int getStrengthBonus() { return strengthBonus; }
    public int getDefenseBonus() { return defenseBonus; }
    public int getDexterityBonus() { return dexterityBonus; }
    public ArrayList<Skill> getClassSkills() { return additionalSkills; }

    public static void classMenu(Player player) {
        Scanner scanner = new Scanner(System.in);
        Class playerClass = player.getCurrentClass();
        boolean inClassMenu = true;
        while (inClassMenu) {
            player.displayInfo();
            System.out.println("========= Class Overview =========");
            System.out.println("// " + playerClass.getName());
            System.out.println(playerClass.getDescription() + "\n");
            System.out.println("========== Stats Bonus ===========");
            System.out.println("> Strength: +" + playerClass.getStrengthBonus());
            System.out.println("> Defense: +" + playerClass.getDefenseBonus());
            System.out.println("> Dexterity: +" + playerClass.getDexterityBonus());
            System.out.println("\n============= Skills =============");
            if (playerClass.getClassSkills() != null) {
                for(int i = 0; i < playerClass.getClassSkills().size() ; i++) {
                    Skill skill = playerClass.getClassSkills().get(i);
                    System.out.println("[" + (i + 1) + "] " + skill.getName() + " (" + skill.getManaCost() + " Mana, " + skill.getCooldown() + " Turn(s) Cooldown)");
                    System.out.println("  - " + skill.getDescription());
                }
            } else {
                System.out.println("> This class has no additional skill.");
            }
            
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
