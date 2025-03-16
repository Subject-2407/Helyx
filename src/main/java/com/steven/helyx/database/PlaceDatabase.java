package com.steven.helyx.database;

import java.util.ArrayList;

import com.steven.helyx.locations.Dungeon;
import com.steven.helyx.locations.Forest;
import com.steven.helyx.locations.Guild;
import com.steven.helyx.locations.Place;
import com.steven.helyx.locations.Shop;
import com.steven.helyx.locations.Tavern;

public class PlaceDatabase {
    public static ArrayList<Place> eldoriaPlaces() {
        ArrayList<Place> places = new ArrayList<>();
        places.add(new Tavern("Moonlit Ember", "A warm and elegant inn. (Restore health & energy for 40 gold)", 40));
        places.add(new Shop("Emberforge", "We sell best quality equipments and items! (Shop)", ItemDatabase.emberforgeItems()));
        places.add(new Guild("Stormbringers", "A guild known for its powerful warriors who bring chaos to battle. (Guild)", ClassDatabase.beginnerClasses()));
        places.add(new Forest("Eldertree", "Home to a massive ancient tree said to contain untold wisdom. (Forest)", 1));
        places.add(new Dungeon("Churenim Cavern", "A deep cavern where the cries of the lost echo eternally. (Dungeon)", 2, MonsterDatabase.dungeonMonsters()));

        return places;
    }
}
