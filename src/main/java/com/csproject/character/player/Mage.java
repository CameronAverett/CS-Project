package com.csproject.character.player;

import com.csproject.character.CombatAction;

public class Mage extends Player {

    public Mage(String name, int level, int strength, int intelligence, int agility) {
        super(name, level, strength, intelligence, agility);
    }
    public void castFireball() {
        int damage = intelligence * 2;
    }
    public void summonIceBarrier() {
        int barrierStrength = agility * 3;
    }
    public void teleport() {
        if (agility >= 20) {
            System.out.println(name + " teleports to a new location!");
        } else {
            System.out.println(name + " attempts to teleport but fails due to low agility.");
        }
    }
    @Override
    public CombatAction combat() {
        return null;
    }
}
