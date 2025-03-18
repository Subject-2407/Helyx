package com.steven.helyx.characters.skills;

import com.steven.helyx.characters.Monster;
import com.steven.helyx.characters.Player;

public abstract class Skill {
    protected String name;
    protected String description;
    protected int manaCost;
    protected int cooldown;
    protected int currentCooldown;
    
    public Skill(String name, String description, int manaCost, int cooldown) {
        this.name = name;
        this.description = description;
        this.manaCost = manaCost;
        this.cooldown = cooldown;
        this.currentCooldown = 0;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getManaCost() { return manaCost; }
    public int getCooldown() { return cooldown; }
    public int getCurrentCooldown() { return currentCooldown; }
    public boolean isInCooldown() { return currentCooldown > 0; }

    public void startCooldown() {
        this.currentCooldown = cooldown;
    }

    public void resetCooldown() {
        this.currentCooldown = 0;
    }

    public void decreaseCooldown() {
        this.currentCooldown -= 1;
        if (currentCooldown <= 0) currentCooldown = 0;
    }

    public abstract void use(Player player, Monster monster);
}
