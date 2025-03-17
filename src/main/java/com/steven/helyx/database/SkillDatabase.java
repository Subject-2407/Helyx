package com.steven.helyx.database;

import java.util.ArrayList;

import com.steven.helyx.characters.skills.OffensiveSkill;
import com.steven.helyx.characters.skills.Skill;

public class SkillDatabase {

    public static ArrayList<Skill> noviceSkills() {
        ArrayList<Skill> skills = new ArrayList<>();

        skills.add(new OffensiveSkill("Heavy Attack", "Hits 1.5x stronger than basic attack.", 5, 3, 0.5));
        return skills;
    }
    
}
