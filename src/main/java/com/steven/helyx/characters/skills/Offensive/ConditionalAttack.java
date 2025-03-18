package com.steven.helyx.characters.skills.Offensive;

import com.steven.helyx.characters.Monster;
import com.steven.helyx.characters.Player;
import com.steven.helyx.characters.skills.OffensiveSkill;
import com.steven.helyx.game.PVE;

public class ConditionalAttack extends OffensiveSkill {
    private double bonusMultiplier;
    private int conditionThreshold;
    private ConditionType conditionType;

    public enum ConditionType {
        HP_BELOW // bonus multiplier when enemy HP is below x
    }
    public ConditionalAttack(String name, String description, int manaCost, int cooldown, int bonusHitChance, double damageMultiplier, double bonusMultiplier, int conditionThreshold, ConditionType conditionType){
        super(name, description, manaCost, cooldown, bonusHitChance, damageMultiplier);
        this.bonusMultiplier = bonusMultiplier;
        this.conditionThreshold = conditionThreshold;
        this.conditionType = conditionType;
    }

    public void use(Player player, Monster monster) {
        if (PVE.playerHitChance(player, monster, bonusHitChance) == 0) {
            System.out.println("> You tried to use " + this.name + " on " + monster.getName() + " but missed!");
            PVE.monsterAttackTurn(monster, player);
            return;
        }

        int playerBaseDamage = player.attack();
        double skillDamage = playerBaseDamage * this.getDamageMultiplier();

        switch (conditionType) {
            case HP_BELOW:
                if (monster.getHP() <= (int)(monster.getMaxHP() * conditionThreshold / 100.0)) {
                    skillDamage = playerBaseDamage * this.bonusMultiplier;
                }
                break;
        }

        int totalDamage = playerBaseDamage + (int)skillDamage;
        this.processBattle(player, monster, totalDamage);

    }
    
}
