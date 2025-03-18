package com.steven.helyx.characters.skills;

public abstract class OffensiveSkill extends Skill {
    protected boolean guaranteedHit;
    protected double damagePercentage;
    
    public OffensiveSkill(String name, String description, int manaCost, int cooldown, boolean guaranteedHit, double damagePercentage) {
        super(name, description, manaCost, cooldown);
        this.guaranteedHit = guaranteedHit;
        this.damagePercentage = damagePercentage;
    }

    public boolean isGuaranteedHit() { return guaranteedHit; }
    public double getDamagePercentage() { return damagePercentage; }
}
