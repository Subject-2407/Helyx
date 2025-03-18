package com.steven.helyx.database;

import java.util.ArrayList;

import com.steven.helyx.locations.Place;
import com.steven.helyx.locations.places.Dungeon;
import com.steven.helyx.locations.places.Forest;
import com.steven.helyx.locations.places.Guild;
import com.steven.helyx.locations.places.Shop;
import com.steven.helyx.locations.places.Tavern;

public class PlaceDatabase {
    public static ArrayList<Place> eldoriaPlaces() {
        ArrayList<Place> places = new ArrayList<>();
        places.add(new Tavern("Moonlit Ember", "A warm and elegant inn. (Restore health, mana, energy for 50 gold)", 50));
        places.add(new Shop("Emberforge", "We sell best quality equipments and items! (Shop)", ItemDatabase.emberforgeItems()));
        places.add(new Guild("Stormbringers", "A guild known for its powerful warriors who bring chaos to battle. (Guild)", ClassDatabase.beginnerClasses()));
        places.add(new Forest("Eldertree", "Home to a massive ancient tree said to contain untold wisdom. (Forest)", 1));
        places.add(new Dungeon("Churenim Cavern", "A deep cavern where the cries of the lost echo eternally. (Dungeon)", 1, MonsterDatabase.dungeonMonsters()));

        return places;
    }
}
