package com.steven.helyx.locations.places;

import java.util.Scanner;

import com.steven.helyx.characters.Monster;
import com.steven.helyx.characters.Player;
import com.steven.helyx.game.*;
import com.steven.helyx.locations.Place;
import com.steven.helyx.utilities.UserInterface;

import java.util.ArrayList;
import java.util.Random;

public class Dungeon extends Place {
    private int energyRequired;
    private Random random;
    private ArrayList<Monster> monsters;

    public Dungeon(String name, String description, int energyRequired, ArrayList<Monster> monsters) {
        super(name, description);
        this.energyRequired = energyRequired;
        this.monsters = monsters;
        this.random = new Random();
    }

    public int getEnergyRequired() { return energyRequired; }
    
    public void explore(Player player) {
        if (player.getEnergy() >= energyRequired) {
            boolean keepExploring = true;
            while (keepExploring && player.isAlive()) {
                Scanner scanner = new Scanner(System.in);

                player.displayInfo();
                if (player.getEnergy() < energyRequired) {
                    System.out.println("> You don't have enough energy to continue exploring.");
                    System.out.println("> Exiting from " + this .name + ".");
                    UserInterface.enterReturn();
                    break;
                }
                System.out.println("> Exploring through " + this.name + "..");
                try { Thread.sleep(1000 + random.nextInt(5001)); } catch (InterruptedException e) {}

                Monster template = monsters.get(random.nextInt(monsters.size()));
                Monster monster = new Monster(template);
                
                System.out.println("> You encountered " + monster.getName() + "! (Battle Power: " + monster.getBattlePower() + ")\n");
                System.out.println("[1] Fight");
                System.out.println("[2] Run");
                System.out.println("[3] Exit dungeon (-" + energyRequired + " Energy)");

                System.out.print("\nEnter choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine();
                System.out.println();

                if (choice == 2) {
                    if (!PVE.attemptEscape(player, monster)) {
                        PVE.battle(player, monster);
                    }
                } else if (choice == 3) {
                    keepExploring = false;
                }
                else if (choice == 1){
                    PVE.battle(player, monster);
                }
                player.reduceEnergy(energyRequired);
            }
            return;
        } else {
            System.out.println("You don't have enough energy to travel here!");
            UserInterface.enterReturn();
            return;
        }
    }
}
