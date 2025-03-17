package com.steven.helyx.characters;

import java.util.Random;

public class Monster {
    private String name;
    private int currentHP;
    private int maxHP;
    private int baseAttack;
    private int baseDexterity;
    private int baseDefense;
    private int expReward;
    private int goldReward;
    private Random random;

    public Monster(String name, int hp, int baseAttack, int baseDexterity, int baseDefense, int expReward, int goldReward) {
        this.name = name;
        this.maxHP = hp;
        this.currentHP = maxHP;
        this.baseAttack = baseAttack;
        this.baseDexterity = baseDexterity;
        this.baseDefense = baseDefense;
        this.expReward = expReward;
        this.goldReward = goldReward;
        this.random = new Random();
    }

    public Monster(Monster other) {
        this.name = other.name;
        this.maxHP = other.maxHP;
        this.currentHP = other.maxHP;
        this.baseAttack = other.baseAttack;
        this.baseDexterity = other.baseDexterity;
        this.baseDefense = other.baseDefense;
        this.expReward = other.expReward;
        this.goldReward = other.goldReward;
        this.random = new Random();
    }

    public String getName() {
        return name;
    }

    public int getHP() {
        return currentHP;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public int attack() {
        int dexterity = baseDexterity;
        boolean isCritical = random.nextInt(100) < Math.sqrt(dexterity) * 3;
        if (isCritical) {
            System.out.println("> !! Monster Critical Hit !!");
            return baseAttack * 2;
        }
        return baseAttack;
    }

    public int takeDamage(int rawDamage) {
        int finalDamage = Math.max(rawDamage - baseDefense, 1);
        int damageDifference = rawDamage - finalDamage;
        currentHP -= finalDamage;
        if (currentHP < 0) currentHP = 0;
        return damageDifference;
    }

    public int getBaseAttack() {
        return baseAttack;
    }

    public int getDexterity() {
        return baseDexterity;
    }

    public int getDefense() {
        return baseDefense;
    }

    public int getExpReward() {
        return expReward;
    }

    public int getGoldReward() {
        return goldReward + random.nextInt(5);
    }

    public boolean isAlive() {
        return currentHP > 0;
    }
}

