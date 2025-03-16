package com.steven.helyx.items;

import com.steven.helyx.characters.*;
import com.steven.helyx.utilities.*;

public class Usable extends Item {
    private int type; // 1: Health Potion, 2: Energy Potion, 3: Golden Potion
    private int bonusPoints;

    public Usable(String name, String description, int price, int type, int bonus) {
        super(name, description, price);
        this.type = type;
        this.bonusPoints = bonus;
    }

    public int getType() {
        return type;
    }

    public int getBonusPoints() {
        return bonusPoints;
    }

    @Override
    public Usable clone() {
        return new Usable(name, description, price, type, bonusPoints);
    }

    @Override
    public void use(Player player) {
        System.out.println("> You used a " + this.getName() + ".");

        switch (this.type) {
            case 1: player.addHP(this.bonusPoints); break;
            case 2: player.gainEnergy(this.bonusPoints); break;
            case 3: 
                player.addHP(this.bonusPoints);
                double energyGain = this.bonusPoints * 0.1; 
                player.gainEnergy((int)energyGain); break;
        }

        UserInterface.enterReturn();
    }
    
}
