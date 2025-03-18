package com.steven.helyx.database;

import java.util.ArrayList;

import com.steven.helyx.characters.skills.Skill;
import com.steven.helyx.characters.skills.Offensive.ConditionalAttack;
import com.steven.helyx.characters.skills.Offensive.HeavyAttack;
import com.steven.helyx.characters.skills.Offensive.ConditionalAttack.ConditionType;
import com.steven.helyx.characters.skills.Utility.BuffSkill;
import com.steven.helyx.characters.skills.Utility.BuffSkill.CalculationType;

public class SkillDatabase {

    public static ArrayList<Skill> noviceSkills() {
        ArrayList<Skill> skills = new ArrayList<>();

        skills.add(new HeavyAttack("Heavy Attack", "Hits 1.5x stronger than basic attack.", 5, 3, 0, 0.5));
  
        return skills;
    }

    public static ArrayList<Skill> mercenarySkills() {
        ArrayList<Skill> skills = new ArrayList<>();

        skills.add(new HeavyAttack("Precision Attack", "A carefully aimed attack, making it more accurate than normal attack.\n    Bonus: +10% Hit Chance, 1.25x Damage.", 3, 1, 10, 0.25));
        skills.add(new ConditionalAttack("Executioner's Strike", "A devastating attack that deals massive damage if the target is weakened. \n    Bonus: 1.5x Damage. 2.0x Damage if target's HP is lower than 50%.", 8, 3, 0, 0.5, 1.0, 50, ConditionType.HP_BELOW));
        skills.add(new BuffSkill("Adrenaline Boost", "A surge of adrenaline boosts attack power and dexterity for a short time.\n    Bonus: +25% Damage, +20% Dexterity for 3 turns.", 10, 4, 3, 25, 0, 20, CalculationType.BATTLE_POWER));

        return skills;
    }
    
}
