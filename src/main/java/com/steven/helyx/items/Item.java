package com.steven.helyx.items;

import com.steven.helyx.characters.*;

public abstract class Item {
    protected String name;
    protected String description;
    protected int price;

    public Item(String name, String description, int price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }

    public abstract void use(Player player);
}