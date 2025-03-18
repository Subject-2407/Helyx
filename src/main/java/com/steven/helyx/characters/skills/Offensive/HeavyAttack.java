package com.steven.helyx.characters.skills.Offensive;

import com.steven.helyx.characters.Monster;
import com.steven.helyx.characters.Player;
import com.steven.helyx.characters.skills.OffensiveSkill;
import com.steven.helyx.game.PVE;

public class HeavyAttack extends OffensiveSkill {
    public HeavyAttack(String name, String description, int manaCost, int coolDown, boolean guaranteedHit, double damagePercentage) {
        super(name, description, manaCost, coolDown, guaranteedHit, damagePercentage);
    }

    @Override
    public void use(Player player, Monster monster) {
        if (!guaranteedHit) {
            if (PVE.playerHitChance(player, monster) == 0) {
                System.out.println("> You tried to use " + this.name + " on " + monster.getName() + " but missed!");
                PVE.monsterAttackTurn(monster, player);
                return;
            } 
        }

        int playerAttack = player.attack();

        double skillDamage = playerAttack * this.getDamagePercentage();
        int totalDamage = playerAttack + (int)skillDamage;
        int playerDamage = PVE.damageVariance(totalDamage);
        int damageDifference = monster.takeDamage(playerDamage);
        String damageReduction = damageDifference > 0 ? "(-" + damageDifference + ")" : "";
        System.out.println("> You used " + this.name + " on " + monster.getName() + "! They took " + playerDamage + damageReduction + " damage!");
        PVE.monsterAttackTurn(monster, player);
    }
    
}
