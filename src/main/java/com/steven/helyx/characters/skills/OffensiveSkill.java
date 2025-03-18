package com.steven.helyx.characters.skills;

import com.steven.helyx.characters.Monster;
import com.steven.helyx.characters.Player;
import com.steven.helyx.game.PVE;

public abstract class OffensiveSkill extends Skill {
    protected int bonusHitChance; // in percentage (e.g. 10 as in 10%)
    protected double damageMultiplier; // multiplier (e.g. : 0.2, 0.5, etc.)
    
    public OffensiveSkill(String name, String description, int manaCost, int cooldown, int bonusHitChance, double damageMultiplier) {
        super(name, description, manaCost, cooldown);
        this.bonusHitChance = bonusHitChance;
        this.damageMultiplier = damageMultiplier;
    }

    public int getBonusHitChance() { return bonusHitChance; }
    public double getDamageMultiplier() { return damageMultiplier; }

    protected void processBattle(Player player, Monster monster, int totalDamage) {
        int variedDamage = PVE.damageVariance(totalDamage);
        int damageDifference = monster.takeDamage(variedDamage);
        String damageReduction = damageDifference > 0 ? "(-" + damageDifference + ")" : "";
        System.out.println("> You used " + this.name + " on " + monster.getName() + "! They took " + variedDamage + damageReduction + " damage!");
        PVE.monsterAttackTurn(monster, player);
    }
}
