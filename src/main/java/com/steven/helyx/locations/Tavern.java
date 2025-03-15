package com.steven.helyx.locations;

import com.steven.helyx.characters.*;
import com.steven.helyx.utilities.UserInterface;

public class Tavern extends Place{
    public Tavern(String name, String description) {
        super(name, description);
    }

    public void explore(Player player) {
        if (player.getGolds() > 0) {
            System.out.println("> You spent 20 golds to rest at the " + name + " Tavern. Health and Energy restored.");
            player.takeGold(20);
            player.setHP(player.getMaxHP());
            player.setEnergy(player.getMaxEnergy());
        } else {
            System.out.println("> You don't have enough gold to rest at the " + name + " Tavern!");
        }
        UserInterface.enterReturn();
        return;
    }
}
