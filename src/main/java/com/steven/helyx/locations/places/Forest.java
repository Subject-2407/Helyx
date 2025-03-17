package com.steven.helyx.locations.places;

import com.steven.helyx.characters.*;
import com.steven.helyx.locations.Place;
import com.steven.helyx.utilities.UserInterface;

public class Forest extends Place{
    private int energyRequired;

    public Forest(String name, String description, int energyRequired) {
        super(name, description);
        this.energyRequired = energyRequired;
    }

    public void explore(Player player) {
        if (player.getEnergy() >= energyRequired) {
            System.out.println("> You go on an adventure, but nothing special happens.");
            UserInterface.enterReturn();
            player.reduceEnergy(energyRequired);
            return;
        } else {
            System.out.println("> You don't have enough energy to travel here!");
            UserInterface.enterReturn();
            return;
        }
        
    }
}
