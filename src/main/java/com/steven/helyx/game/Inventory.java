package com.steven.helyx.game;

import com.steven.helyx.characters.*;
import com.steven.helyx.items.*;
import com.steven.helyx.utilities.UserInterface;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private List<Item> items;

    public Inventory() {
        this.items = new ArrayList<>();
    }

    public void addItem(Item item) {
        items.add(item);
        System.out.println("> " + item.getName() + " has been added to your inventory.");
    }

    public void showInventory(Player player) {
        System.out.println("==== Inventory ====\n");
        if (items.isEmpty()) {
            System.out.println("Your inventory is empty.");
        } else {
            for (int i = 0; i < items.size(); i++) {
                Item item = items.get(i);
                String equippedIndicator = "";
                String equipmentStats = "";

                if (item instanceof Equipment) {
                    Equipment equip = (Equipment) item;
                    equipmentStats = " [ATK: " + equip.getAttackBonus() + ", DEF: " + equip.getDefenseBonus() + "]";
                    if (equip == player.getEquippedWeapon() || equip == player.getEquippedArmor()) {
                        equippedIndicator = " (EQUIPPED)";
                    }
                }

                System.out.println("[" + (i + 1) + "] " + item.getName() + " - " + item.getDescription() + equipmentStats +  equippedIndicator);
            }
        }
    }

    public void useItem(int index, Player player) {
        if (index >= 0 && index < items.size()) {
            Item item = items.get(index);
            item.use(player);

            if (item instanceof com.steven.helyx.items.Usable) {
                items.remove(index);
            }
        } else {
            System.out.println("Invalid index! Choose a valid item number.");
            UserInterface.enterReturn();
        }
    }

    public void removeItem(int index) {
        if (index >= 0 && index < items.size()) {
            System.out.println(items.get(index).getName() + " has been removed from your inventory.");
            items.remove(index);
        } else {
            System.out.println("Invalid index! Choose a valid item number.");
        }
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }
}
