package com.steven.helyx.database;

import java.util.ArrayList;

import com.steven.helyx.locations.Area;

public class AreaDatabase {
    public static ArrayList<Area> beginnerAreas() {
        ArrayList<Area> areas = new ArrayList<>();
        areas.add(new Area("Eldoria Plains", "Vast golden fields with a gentle breeze.", PlaceDatabase.eldoriaPlaces()));
        areas.add(new Area("Scorched Dunes", "A harsh, endless desert with shifting sands."));
        areas.add(new Area("Ashen Wastes", "A land covered in black volcanic ash and ruins."));
        areas.add(new Area("Frozen Tundra", "A vast frozen wasteland of eternal winter."));

        return areas;
    }
    
}
