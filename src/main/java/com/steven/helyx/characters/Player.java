package com.steven.helyx.characters;

import com.steven.helyx.game.*;
import com.steven.helyx.items.*;
import com.steven.helyx.utilities.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class Player {
    private String name;
    private String playerClass;
    private int level;
    private int xp;
    private int gold;
    private int currentEnergy;
    private int maxEnergy;
    private int maxHP;
    private int currentHP;
    private Map<String, Integer> playerStats;
    private int playerPoints;
    private Equipment equippedWeapon;
    private Equipment equippedArmor;
    private Inventory inventory;
    private Random random;

    public Player(String name, String playerClass) {
        this.name = name;
        this.playerClass = playerClass;
        this.level = 1;
        this.xp = 0;
        this.gold = 1000;
        this.maxEnergy = 10;
        this.currentEnergy = maxEnergy;
        this.playerStats = new LinkedHashMap<>();

        playerStats.put("Strength", 5); // base damage
        playerStats.put("Dexterity", 5); // critical chance & evasion
        playerStats.put("Defense", 5); // base defense
        playerStats.put("Constitution", 0); // increases health
        this.playerPoints = 2;

        this.maxHP = 100;
        this.currentHP = maxHP;
        this.inventory = new Inventory();
        this.random = new Random();
    }

    public String getName() {
        return name;
    }

    public void gainXP(int amount) {
        xp += amount;
        levelUp();
    }

    private void levelUp() {
        int xpThreshold = level * 100;
        if (xp >= xpThreshold) {
            level++;
            playerPoints += 2;
            xp -= xpThreshold;
            currentHP = maxHP;
        }
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void displayInfo() {
        UserInterface.clearConsole();
        System.out.println("========================");
        System.out.println(name + " (" + playerClass + " Class)");
        System.out.println("Level: " + level + " (XP: " + xp + "/" + level * 100 + ")");
        System.out.println("HP: " + currentHP + "/" + maxHP + "  Gold: " + gold);
        System.out.println("Energy: " + currentEnergy + "/" + maxEnergy);
        System.out.println("========================\n");
    }

    public boolean isAlive() {
        return currentHP > 0;
    }

    public int attack() {
        int strength = playerStats.get("Strength");
        int dexterity = playerStats.get("Dexterity") <= 20 ? playerStats.get("Dexterity") : 20;
        int weaponDamage = equippedWeapon != null ? equippedWeapon.getAttackBonus() : 0;

        // base damage
        int baseDamage = strength + weaponDamage;

        // critical chance
        boolean isCritical = random.nextInt(100) < dexterity * 2;
        if (isCritical) {
            System.out.println(">> !! Critical Hit !!");
            return baseDamage * 2;
        }

        return baseDamage;
    }

    public void equipItem(Equipment newEquipment) {
        if (newEquipment == null) return;

        switch (newEquipment.getType()) {
            case 1: // weapon
                if (equippedWeapon != null) {
                    equippedWeapon.unequip();
                    System.out.println("> You unequipped " + equippedWeapon.getName() + ".");
                }
                if (equippedWeapon == newEquipment) {
                    equippedWeapon = null;
                } else {
                    equippedWeapon = newEquipment;
                    equippedWeapon.equip();
                    System.out.println("> You equipped " + equippedWeapon.getName() + ".");
                }
                break;
            case 2: // armor
                if (equippedArmor != null) {
                    equippedArmor.unequip();
                    System.out.println("> You unequipped " + equippedArmor.getName() + ".");
                }
                if (equippedArmor == newEquipment) {
                    equippedArmor = null;
                } else {
                    equippedArmor = newEquipment;
                    equippedArmor.equip();
                    System.out.println("> You equipped " + equippedArmor.getName() + ".");
                }
                break;
            default:
                System.out.println("> This item can't be equipped.");
        }

        UserInterface.enterReturn();
    }

    public void useItem(Usable usableItem) {
        System.out.println("> You used a " + usableItem.getName() + ".");

        switch (usableItem.getType()) {
            case 1: addHP(usableItem.getBonusPoints()); break;
            case 2: gainEnergy(usableItem.getBonusPoints()); break;
            case 3: 
                addHP(usableItem.getBonusPoints());
                double energyGain = usableItem.getBonusPoints() * 0.1; 
                gainEnergy((int)energyGain); break;
        }

        UserInterface.enterReturn();
    }

    public Equipment getEquippedWeapon() {
        return equippedWeapon;
    }

    public Equipment getEquippedArmor() {
        return equippedArmor;
    }

    public int getEnergy() {
        return currentEnergy;
    }

    public int getMaxEnergy() {
        return maxEnergy;
    }

    public void gainEnergy(int energy) {
        currentEnergy += energy;
        if (currentEnergy > maxEnergy) currentEnergy = maxEnergy;
    }

    public void setEnergy(int energy) {
        currentEnergy = energy;
        if (currentEnergy > maxEnergy) currentEnergy = maxEnergy;
    }

    public void reduceEnergy(int energy) {
        currentEnergy -= energy;
        if (currentEnergy < 0) currentEnergy = 0;
    }

    public int getGolds() {
        return gold;
    }

    public void takeGold(int golds) {
        gold -= golds;
        if (gold < 0) gold = 0;
    }

    public void gainGold(int golds) {
        gold += golds;
    }

    public int getStats(String stats) {
        return playerStats.get(stats);
    }

    public int getBaseDefense() {
        int additionalDefense = equippedArmor != null ? equippedArmor.getDefenseBonus() : 0;
        return playerStats.get("Defense") + additionalDefense;
    }

    public int getCurrentHP() {
        return currentHP;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public void addHP(int hp) {
        currentHP += hp;
        if (currentHP > maxHP) currentHP = maxHP;
    }

    public void setHP(int hp) {
        currentHP = hp;
        if (currentHP > maxHP) currentHP = maxHP;
    }

    public int takeDamage(int rawDamage) { // armor def contributes 60%, weapon def contributes 30%, player def contributes 10%
        int armorDefense = equippedArmor != null ? equippedArmor.getDefenseBonus() : 0;
        int weaponDefense = equippedWeapon != null ? equippedWeapon.getDefenseBonus() : 0;
        int playerDefense = playerStats.get("Defense");
        double reductionPercentage = ((armorDefense * 0.6) + (weaponDefense * 0.3) + (playerDefense * 0.1)) / (rawDamage + 1);
        int finalDamage = Math.max(1, (int) (rawDamage * (1 - reductionPercentage)));
        currentHP -= finalDamage;
        if (currentHP < 0) currentHP = 0;

        int damageDifference = rawDamage - finalDamage;
        return damageDifference;
    }

    public void allocateStatPoint(int index) {
        List<String> keys = new ArrayList<>(playerStats.keySet());
        String stat = keys.get(index);
        if (playerPoints > 0 && playerStats.containsKey(stat)) {
            playerStats.put(stat, playerStats.get(stat) + 1);
            playerPoints--;
            if (stat.equals("Constitution")) {
                maxHP = 100 + playerStats.get("Constitution") * 10;
                currentHP = maxHP;
            }
            System.out.println("> " + stat + " increased! Remaining points: " + playerPoints);
            UserInterface.enterReturn();
        } else {
            System.out.println("Invalid allocation or no points left.");
            UserInterface.enterReturn();
        }
    }

    public void displayStatsMenu(Scanner scanner) {
        boolean inStatsMenu = true;
        while (inStatsMenu) {
            displayInfo();
            System.out.println("==== Stats ====");
            System.out.println("Player Points: " + playerPoints);
            System.out.println();
            int i = 0; for (String stat : playerStats.keySet()) {
                i++;
                System.out.print("["+ i +"] " + stat + ": " + playerStats.get(stat));
                if (stat.equals("Strength") && equippedWeapon != null) {
                    System.out.println(" (+"+ equippedWeapon.getAttackBonus() +")");
                } else if (stat.equals("Defense") && equippedArmor != null) {
                    System.out.println(" (+"+ equippedArmor.getDefenseBonus() +")");
                } else System.out.println();
            }
            System.out.println("\n[0] Return to Main Menu");
            System.out.print("\nEnter stats to allocate on: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();
            if (choice == 0) { 
                inStatsMenu = false; 
                break; 
            } else if (choice > 0 && (choice - 1) < i){ 
                choice--; 
                allocateStatPoint(choice); 
            } else {
                System.out.println("Invalid choice!");
                UserInterface.enterReturn();
            }
        }
    }
}
