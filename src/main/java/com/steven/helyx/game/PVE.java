package com.steven.helyx.game;

import com.steven.helyx.characters.*;
import com.steven.helyx.characters.skills.OffensiveSkill;
import com.steven.helyx.characters.skills.Skill;
import com.steven.helyx.characters.skills.Utility.BuffSkill;
import com.steven.helyx.utilities.*;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class PVE {
    private static Random random;

    public PVE() {
        random = new Random();
    }

    private static void waitMoment() {
        random = new Random();
        try { Thread.sleep(1000 + (1000 * random.nextInt(3))); } catch (InterruptedException e) {}
    }

    public static void battle(Player player, Monster monster) {
        Scanner scanner = new Scanner(System.in);
        String hasWeapon = player.getEquippedWeapon() != null ? " (" + player.getEquippedWeapon().getName() + ")" : " (Fist)";
        ArrayList<Skill> playerSkills = player.getSkills();
        int turn = 0;
        while (player.isAlive() && monster.isAlive()) {
            player.displayInfo();
            turn++;
            System.out.println("========== In Battle With ==========");
            System.out.println("||  " + monster.getName() + "   (Turn " + turn + ")");
            System.out.println("||  HP: " + monster.getHP() + "/" + monster.getMaxHP() + "  Battle Power: " + monster.getBattlePower());
            System.out.println("====================================");
            System.out.print("\n[F] Attack");
            System.out.print(" | [Q] Block");
            if (playerSkills.size() > 0) { System.out.println("\n"); } else System.out.print("\n"); 
            
            for(int i = 0; i < playerSkills.size(); i++) {
                String skillCooldown = playerSkills.get(i).isInCooldown() ? " - In Cooldown" : "";
                String buffSkillStatus = "";
                if (playerSkills.get(i) instanceof BuffSkill) {
                    BuffSkill buffSkill = (BuffSkill) playerSkills.get(i);
                    if (buffSkill.getBuffTimer() > 0) buffSkillStatus = " (Active)";
                }
                System.out.println("[" + (i + 1) + "] " + playerSkills.get(i).getName() + " (" + playerSkills.get(i).getManaCost() + " Mana)" + buffSkillStatus + skillCooldown);
            }

            System.out.print("\nEnter choice: ");
            String choice = scanner.nextLine().trim().toLowerCase();
            System.out.println();

            if (choice.matches("f")) { // attacking
                if (playerHitChance(player, monster, 0) == 1) {
                    int playerDamage = damageVariance(player.attack());
                    int damageDifference = monster.takeDamage(playerDamage);
                    String damageReduction = damageDifference > 0 ? "(-" + damageDifference + ")" : ""; 
                    System.out.println("> You hit the " + monster.getName() + " for " + playerDamage + damageReduction + " damage!" + hasWeapon);
                } else {
                    System.out.println("> You missed your attack!");
                }

                monsterAttackTurn(monster, player);
                decreaseSkillsCooldown(playerSkills);
                player.decreaseBuffsTimer();
            } else if (choice.matches("q")) { // blocking
                System.out.println("> Waiting opponent to attack..");
                waitMoment();
                if (monster.isAlive()) {
                    if (playerBlockChance(player, monster) == 1) {
                        if (monsterHitChance(monster, player) == 1) {
                            System.out.println("> The " + monster.getName() + " hits you!");
                            System.out.println("> You successfully blocked their attack!");
                            player.gainMana((int)(player.getMaxMana() * 0.05)); // successfull block replenish 5% mana;
                        } else {
                            System.out.println("> The " + monster.getName() + " missed its attack!");
                        }
                    } else {
                        if (monsterHitChance(monster, player) == 1) {
                            int monsterRawDamage = damageVariance(monster.attack());
                            int damageDifference = player.takeDamage(monsterRawDamage);
                            String damageReduction = damageDifference > 0 ? "(-" + damageDifference + ")" : "";
                            System.out.println("> The " + monster.getName() + " hits you for " + monsterRawDamage + damageReduction + " damage!");
                            System.out.println("> You failed to block their attack!");
                        } else {
                            System.out.println("> The " + monster.getName() + " missed its attack!");
                        }
                    }
                }
                if (!player.isAlive()) { break; }
                UserInterface.enterReturn();
                decreaseSkillsCooldown(playerSkills);
                player.decreaseBuffsTimer();
            } else if (choice.matches("[1-" + playerSkills.size() + "]") && playerSkills.size() > 0) { // use skill
                int skillChoice = Integer.parseInt(choice);
                useSkill(player, monster, playerSkills.get(skillChoice - 1));
            } else { // wrong choice (turn wasted)
                System.out.println("> You wasted a turn..");
                waitMoment();
                if (monster.isAlive()) {
                    if (monsterHitChance(monster, player) == 1) {
                        int monsterRawDamage = damageVariance(monster.attack());
                        int playerDamageReduction = player.takeDamage(monsterRawDamage);
                        String playerReduction = playerDamageReduction > 0 ? "(-" + playerDamageReduction + ")" : ""; 
                        
                        System.out.println("> The " + monster.getName() + " hits you for " + monsterRawDamage + playerReduction + " damage!");
                        if (player.isAlive()) { UserInterface.enterReturn(); }
                    } else {
                        System.out.println("> The " + monster.getName() + " missed its attack!");
                        UserInterface.enterReturn();
                    }
                }
                decreaseSkillsCooldown(playerSkills);
                player.decreaseBuffsTimer();
            }
        }

        

        if (!player.isAlive()) {
            System.out.println("> You have been defeated..");
            UserInterface.enterReturn();
        } else {
            int gainXP = monster.getExpReward();
            int gainGold = monster.getGoldReward();
            player.gainXP(gainXP);
            player.gainGold(gainGold);
            player.gainMana((int)(player.getMaxMana() * 0.1));
            resetSkillsCooldown(playerSkills);
            player.resetBuffs();
            System.out.println("> You defeated the " + monster.getName() + "! Received " + gainXP + " XP and found " + gainGold + " golds.");
            UserInterface.enterReturn();
        }
    }

    public static void useSkill(Player player, Monster monster, Skill skill) {
        if (!(player.getMana() >= skill.getManaCost())) {
            System.out.println("> You don't have enough mana to use this skill! You wasted a turn..");
            monsterAttackTurn(monster, player);
            decreaseSkillsCooldown(player.getSkills());
            player.decreaseBuffsTimer();
            return;
        }

        if (skill.isInCooldown()) {
            System.out.println("> Your skill is currently in cooldown! You wasted a turn..");
            monsterAttackTurn(monster, player);
            decreaseSkillsCooldown(player.getSkills());
            player.decreaseBuffsTimer();
            return;
        }

        for(int i = 0; i < player.getSkills().size(); i++) {
            if (player.getSkills().get(i) == skill) {
                continue;
            } else {
                player.getSkills().get(i).decreaseCooldown();
            }
        }
        
        player.reduceMana(skill.getManaCost());
        player.decreaseBuffsTimer();
        skill.use(player, monster);
        skill.startCooldown();
    }

    private static void resetSkillsCooldown(ArrayList<Skill> playerSkills) {
        for(int i = 0; i < playerSkills.size(); i++) {
            playerSkills.get(i).resetCooldown();
        }
    }

    private static void decreaseSkillsCooldown(ArrayList<Skill> playerSkills) {
        for(int i = 0; i < playerSkills.size(); i++) {
            playerSkills.get(i).decreaseCooldown();
        }
    }

    public static void monsterAttackTurn(Monster monster, Player player) {
        if (monster.isAlive()) {
            waitMoment();
            if (monsterHitChance(monster, player) == 1) {
                int monsterRawDamage = damageVariance(monster.attack());
                int playerDamageReduction = player.takeDamage(monsterRawDamage);
                String playerReduction = playerDamageReduction > 0 ? "(-" + playerDamageReduction + ")" : ""; 
                
                System.out.println("> The " + monster.getName() + " hits you for " + monsterRawDamage + playerReduction + " damage!");
                if (player.isAlive()) { UserInterface.enterReturn(); }
            } else {
                System.out.println("> The " + monster.getName() + " missed its attack!");
                UserInterface.enterReturn();
            }
        }
    }

    public static boolean attemptEscape(Player player, Monster monster) {
        // get both dexterity
        int playerDexterity = player.getDexterity() + player.getBuff("dexterity");
        int monsterDexterity = monster.getDexterity();

        // calculate escape chance
        int escapeChance = 50 + (playerDexterity - monsterDexterity) * 3;
        escapeChance = Math.max(10, Math.min(90, escapeChance));

        // roll it
        random = new Random();
        boolean escaped = random.nextInt(100) < escapeChance;

        if (escaped) {
            // bruise chance
            int bruiseChance = monsterDexterity / 2;
            if (random.nextInt(100) < bruiseChance) {
                int bruiseDamage = (int) (monster.attack() * (0.05 + random.nextDouble() * 0.10));
                int damageDifference = player.takeDamage(bruiseDamage);
                String damageReduction = damageDifference > 0 ? "(-" + damageDifference + ")" : "";
                System.out.println("> You escaped but the " + monster.getName() + " manages to bruise you! Took " + bruiseDamage + damageReduction + " damage.");
                UserInterface.enterReturn();
            } else {
                System.out.println("> You escaped safely!");
                UserInterface.enterReturn();
            }
            return true;
        } else {
            System.out.println("> You failed to escape!");
            UserInterface.enterReturn();
            return false;
        }
    }

    public static int damageVariance(int damage) {
        random = new Random();
        int variance = (int)(damage * 0.1);
        return damage += random.nextInt(variance + 1) - (variance / 2);
    }

    private static int playerBlockChance(Player player, Monster monster) {
        // get monster attack attribute
        int monsterAttack = monster.getBaseAttack();
        // get player attributes
        int playerDefense = player.getDefense() + player.getBuff("defense");
        int weaponDefense = player.getEquippedWeapon() != null ? player.getEquippedWeapon().getDefenseBonus() : 0;

        // calculate block chance
        double blockChance = 30 + ((weaponDefense + playerDefense) / (double) (monsterAttack + 5) * 40);

        // roll it
        random = new Random();
        boolean isBlocked = random.nextInt(100) < blockChance;
        return isBlocked ? 1 : 0;
    }

    public static int playerHitChance(Player player, Monster monster, int bonusHitChance) {
        // get both dexterity
        int playerDexterity = player.getDexterity() + player.getBuff("dexterity");
        int monsterDexterity = monster.getDexterity();

        // calculate hit chance
        int hitChance = 75 + (playerDexterity - monsterDexterity) * 2;
        hitChance = Math.max(10, Math.min(95, hitChance));

        // roll it
        random = new Random();
        if (random.nextInt(100) >= (hitChance + bonusHitChance)) {
            return 0;
        } else return 1;
    }

    private static int monsterHitChance(Monster monster, Player player) {
        // get both dexterity
        int monsterDexterity = monster.getDexterity();
        int playerDexterity = player.getDexterity() + player.getBuff("dexterity");

        // calculate hit chance
        int hitChance = 75 + (monsterDexterity - playerDexterity) * 2;
        hitChance = Math.max(10, Math.min(95, hitChance));
        
        // roll it
        random = new Random();
        if (random.nextInt(100) >= hitChance) {
            return 0;
        } else return 1;
    }

}
