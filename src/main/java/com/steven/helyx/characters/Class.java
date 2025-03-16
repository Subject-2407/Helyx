package com.steven.helyx.characters;

public class Class {
    private String name;
    private String description;
    private int price;
    private int strengthBonus;
    private int defenseBonus;
    private int dexterityBonus;

    public Class(String name, String description, int price, int strengthBonus, int defenseBonus, int dexterityBonus) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.strengthBonus = strengthBonus;
        this.defenseBonus = defenseBonus;
        this.dexterityBonus = dexterityBonus;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getPrice() { return price;}
    public int getStrengthBonus() { return strengthBonus; }
    public int getDefenseBonus() { return defenseBonus; }
    public int getDexterityBonus() { return dexterityBonus; }
}
