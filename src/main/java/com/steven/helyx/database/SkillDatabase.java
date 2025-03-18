package com.steven.helyx.database;

import java.util.ArrayList;

import com.steven.helyx.characters.skills.Skill;
import com.steven.helyx.characters.skills.Offensive.HeavyAttack;

public class SkillDatabase {

    public static ArrayList<Skill> noviceSkills() {
        ArrayList<Skill> skills = new ArrayList<>();

        skills.add(new HeavyAttack("Heavy Attack", "Hits 1.5x stronger than basic attack.", 5, 3, false, 0.5));
        return skills;
    }
    
}
