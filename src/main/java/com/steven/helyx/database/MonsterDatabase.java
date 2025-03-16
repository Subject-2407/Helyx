package com.steven.helyx.database;

import com.steven.helyx.characters.*;
import java.util.ArrayList;
import java.util.Random;

public class MonsterDatabase {
    private static Random random = new Random();

    public static ArrayList<Monster> dungeonMonsters() {
        ArrayList<Monster> monsters = new ArrayList<>();
        monsters.add(new Monster("Goblin", 30, 5, 8, 2, 10, 10));
        monsters.add(new Monster("Slime", 50, 3, 5, 5, 12, 8));
        monsters.add(new Monster("Wolf", 40, 7, 12, 3, 15, 12));
        monsters.add(new Monster("Orc", 80, 12, 7, 6, 25, 20));
        monsters.add(new Monster("Bandit", 60, 10, 14, 4, 20, 18));
        monsters.add(new Monster("Skeleton", 70, 9, 9, 5, 22, 22));
        monsters.add(new Monster("Troll", 120, 18, 5, 10, 35, 30));
        monsters.add(new Monster("Dark Knight", 150, 22, 10, 12, 50, 50));
        return monsters;
    }

    public static Monster getRandomDungeonMonster() {
        ArrayList<Monster> monsters = dungeonMonsters();
        return monsters.get(random.nextInt(monsters.size()));
    }
}

