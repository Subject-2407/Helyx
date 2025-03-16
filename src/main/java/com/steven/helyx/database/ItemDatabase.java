package com.steven.helyx.database;

import java.util.ArrayList;

import com.steven.helyx.items.Equipment;
import com.steven.helyx.items.Item;
import com.steven.helyx.items.Usable;

public class ItemDatabase {
    public static ArrayList<Item> emberforgeItems() {
        ArrayList<Item> items = new ArrayList<>();
        items.add(new Equipment("Iron Sword", "A sword made of iron, much better than the wooden one.", 100, 1, 20, 7));
        items.add(new Equipment("Leather Tunic", "Peasant clothes.", 30, 2, 0, 10));
        items.add(new Usable("Small Health Potion", "Restores 25 HP.",20, 1, 25));
        items.add(new Usable("Energy Potion", "Restores 5 Energy.", 30, 2, 5));
        items.add(new Usable("The Golden Potion", "Restores 50 HP and 5 Energy.", 60, 3, 50));

        return items;
    }
}
