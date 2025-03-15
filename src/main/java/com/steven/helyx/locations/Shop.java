package com.steven.helyx.locations;

import com.steven.helyx.characters.Player;
import com.steven.helyx.game.Inventory;
import com.steven.helyx.items.*;
import com.steven.helyx.utilities.UserInterface;

import java.util.ArrayList;
import java.util.Scanner;

public class Shop extends Place{
    private ArrayList<Item> itemsForSale;
    private Scanner scanner;

    public Shop(String name, String description, ArrayList<Item> itemsForSale) {
        super(name, description);
        this.itemsForSale = itemsForSale;
        this.scanner = new Scanner(System.in);
    }
    
    @Override
    public void explore(Player player) {
        player.displayInfo();
        System.out.println("==== " + name + " Shop ====\n");
        for (int i = 0; i < itemsForSale.size(); i++) {
            Item item = itemsForSale.get(i);
            System.out.println("[" + (i + 1) + "] " + item.getName() + " - " + item.getDescription() + " (" + item.getPrice() + " Gold)");
        }
        System.out.println("\n[0] Exit");

        System.out.print("\nEnter your choice: ");
        int choice = scanner.nextInt();


        if (choice > 0 && choice <= itemsForSale.size()) {
            buyItem(player, itemsForSale.get(choice - 1));
        } else {
            return;
        }
    }

    private void buyItem(Player player, Item item) {
        Inventory inventory = player.getInventory();
        int price = item.getPrice();
        if (player.getGolds() >= price) {
            player.takeGold(price);
            System.out.println("> You bought " + item.getName() + " for " + price + " Gold!");
            inventory.addItem(item);
            UserInterface.enterReturn();
        } else {
            System.out.println("> You don't have enough gold!");
            UserInterface.enterReturn();
        }
    }
}
