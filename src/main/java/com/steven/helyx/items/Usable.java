package com.steven.helyx.items;

import com.steven.helyx.characters.*;

public class Usable extends Item {
    private int type; // 1: Health Potion, 2: Mana Potion, 3: Energy Potion, 4: Golden Potion
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
        player.useItem(this);
    }
    
}
