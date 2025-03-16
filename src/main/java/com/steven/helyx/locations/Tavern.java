package com.steven.helyx.locations;

import com.steven.helyx.characters.*;
import com.steven.helyx.utilities.UserInterface;

public class Tavern extends Place{
    private int price;
    private boolean fullService = false;
    private int healthBonus;
    private int energyBonus;

    public Tavern(String name, String description, int price) {
        super(name, description);
        this.price = price;
        this.fullService = true;
    }

    public Tavern(String name, String description, int price, int healthBonus) {
        super(name, description);
        this.price = price;
        this.healthBonus = healthBonus;
    }

    public Tavern(String name, String description, int price, int healthBonus, int energyBonus) {
        super(name, description);
        this.price = price;
        this.healthBonus = healthBonus;
        this.energyBonus = energyBonus;
    }

    public void explore(Player player) {
        if (player.getGolds() > this.price) {
            
            player.takeGold(this.price);
            if (this.fullService) {
                player.setHP(player.getMaxHP());
                player.setEnergy(player.getMaxEnergy());
            } else {
                player.addHP(this.healthBonus);
                player.gainEnergy(this.energyBonus);
            }

            String restoredStat = "";
            if (this.fullService) {
                restoredStat = " Health and Energy restored.";
            } else {
                if (this.healthBonus > 0) {
                    restoredStat += " +" + this.healthBonus + " Health.";
                }
                if (this.energyBonus > 0) {
                    restoredStat += " +" + this.energyBonus + " Energy.";
                }
            }

            System.out.println("> You spent " + this.price + " gold to rest at the " + name + " Tavern." + restoredStat);
        } else {
            System.out.println("> You don't have enough gold to rest at the " + name + " Tavern!");
        }
        UserInterface.enterReturn();
        return;
    }
}
