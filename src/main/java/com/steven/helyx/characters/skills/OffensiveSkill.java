package com.steven.helyx.characters.skills;

import com.steven.helyx.characters.Monster;
import com.steven.helyx.characters.Player;
import com.steven.helyx.game.PVP;

public class OffensiveSkill extends Skill {
    private double damagePercentage;

    public OffensiveSkill(String name, String description, int manaCost, int cooldown, double damagePercentage) {
        super(name, description, manaCost, cooldown);
        this.damagePercentage = damagePercentage;
    }

    public double getDamagePercentage() { return damagePercentage; }

    @Override
    public void use(Player player, Monster monster) {
        int playerAttack = player.attack();

        double skillDamage = playerAttack * this.damagePercentage;
        int totalDamage = playerAttack + (int)skillDamage;
        int playerDamage = PVP.damageVariance(totalDamage);
        int damageDifference = monster.takeDamage(playerDamage);
        String damageReduction = damageDifference > 0 ? "(-" + damageDifference + ")" : "";
        System.out.println("> You used " + this.name + " on " + monster.getName() + "! They took " + playerDamage + damageReduction + " damage!");

    }
}
