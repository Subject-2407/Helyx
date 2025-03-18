package com.steven.helyx.database;

import com.steven.helyx.characters.Class;

import java.util.ArrayList;

public class ClassDatabase {
    
    public static Class noviceClass() {
        return new Class("Novice", "As the name said, you are a Novice.", 0, 0, 0, 0, SkillDatabase.noviceSkills());
    }

    public static ArrayList<Class> beginnerClasses() {
        ArrayList<Class> classes = new ArrayList<>();
        classes.add(new Class("Mercenary", "A skilled warrior for hire, experienced in battle tactics.", 75, 3, 2, 2, SkillDatabase.mercenarySkills()));
        classes.add(new Class("Warrior", "A battle-hardened fighter with high attack power.", 100, 5, 2, 1));
        classes.add(new Class("Berserker", "A frenzied warrior who thrives in pure aggression.", 150, 7, 1, 2));
        classes.add(new Class("Guardian", "A protector with excellent defense and durability.", 150, 3, 6, 1));
        classes.add(new Class("Rogue", "A nimble warrior relying on speed and quick attacks.", 100, 3, 1, 6));
        return classes;
    }
}
