package com.steven.helyx.items;

import com.steven.helyx.characters.*;

public class Equipment extends Item {
    private int type; // 1: Weapon, 2: Armor, 3: Accessories
    private int attackBonus;
    private int defenseBonus;

    public Equipment(String name, String description, int price, int type, int attackBonus, int defenseBonus) {
        super(name, description, price);
        this.type = type;
        this.attackBonus = attackBonus;
        this.defenseBonus = defenseBonus;
    }

    public int getType() {
        return type;
    }
    public int getAttackBonus() {
        return attackBonus;
    }

    public int getDefenseBonus() {
        return defenseBonus;
    }

    @Override
    public void use(Player player) {
        player.equipItem(this);
    }
}
