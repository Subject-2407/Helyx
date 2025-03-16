package com.steven.helyx.items;

import java.util.UUID;

import com.steven.helyx.characters.*;

public abstract class Item {
    protected String id;
    protected String name;
    protected String description;
    protected int price;

    public Item(String name, String description, int price) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public String getId() {
        return id;
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

    public abstract Item clone();

    public abstract void use(Player player);
}