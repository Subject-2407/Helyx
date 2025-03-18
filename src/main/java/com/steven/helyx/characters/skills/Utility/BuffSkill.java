package com.steven.helyx.characters.skills.Utility;

import com.steven.helyx.characters.Monster;
import com.steven.helyx.characters.Player;
import com.steven.helyx.characters.skills.Skill;
import com.steven.helyx.game.PVE;

public class BuffSkill extends Skill{
    private int effectLength; // in turns
    private int strengthBonus; // in percentage, e.g. 50 for +50% 
    private int defenseBonus;
    private int dexterityBonus;
    private int strengthAddition; // final value to add to player stat
    private int defenseAddition;
    private int dexterityAddition;
    private int buffTimer;
    private CalculationType calculationType;

    public enum CalculationType {
        BASE_STATS, // calculated separately from equipments
        BATTLE_POWER // calculated with base stats + equipments bonus
    }

    public BuffSkill(String name, String description, int manaCost, int cooldown, int effectLength, int strengthBonus, int defenseBonus, int dexterityBonus, CalculationType calculationType) {
        super(name, description, manaCost, cooldown);
        this.effectLength = effectLength;
        this.strengthBonus = strengthBonus;
        this.defenseBonus = defenseBonus;
        this.dexterityBonus = dexterityBonus;
        this.calculationType = calculationType;
    }

    public int getStrengthBuff() { return strengthAddition; }
    public int getDefenseBuff() { return defenseAddition; }
    public int getDexterityBuff() { return dexterityAddition; }

    public int getBuffTimer() { return buffTimer; }
    public void startBuffTimer() { this.buffTimer = effectLength; }
    public void decreaseBuffTimer() {
        this.buffTimer--;
        if (buffTimer < 0) buffTimer = 0;
    }
    public void resetBuffTimer() { buffTimer = 0; }


    public void use(Player player, Monster monster) {
        this.dexterityAddition = (int)(player.getDexterity() * (dexterityBonus / 100.0)); // have to put this here for now
        switch (this.calculationType) {
            case BASE_STATS:
                this.strengthAddition = (int)(player.getStrength() * (strengthBonus / 100.0));
                this.defenseAddition = (int)(player.getDefense() * (defenseBonus / 100.0));
                break;
            case BATTLE_POWER:
                int playerWeaponAttack = player.getEquippedWeapon() != null ? player.getEquippedWeapon().getAttackBonus() : 0;
                int playerArmorDefense = player.getEquippedArmor() != null ? player.getEquippedArmor().getDefenseBonus() : 0;
                this.strengthAddition = (int)((playerWeaponAttack + player.getStrength()) * (strengthBonus / 100.0));
                this.defenseAddition = (int)((playerArmorDefense + player.getDefense()) * (defenseBonus / 100.0));
                break;
        }

        this.startBuffTimer();
        player.addBuff(this);
        System.out.println("> You used " + this.name + "! Buff effect will be active for " + effectLength + " turn(s).");
        PVE.monsterAttackTurn(monster, player);
    }
    
}
