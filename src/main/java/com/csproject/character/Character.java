package com.csproject.character;

import com.csproject.character.effects.Effect;
import com.csproject.character.effects.StatusEffect;
import com.csproject.character.player.Player;
import com.csproject.game.Game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Character {

    public static final String STRENGTH = "Strength";
    public static final String INTELLIGENCE = "Intelligence";
    public static final String AGILITY = "Agility";

    public static final List<String> CHARACTER_ATTRS = List.of(STRENGTH, INTELLIGENCE, AGILITY);

    private static final double BASE_HP = 100;
    private static final double HP_PER_LEVEL = 10;

    private static final double MANA_PER_INTELLIGENCE = 25;

    private double hp;
    private double mana;
    private int level;

    protected final CharacterAttribute strengthAttr;
    protected final CharacterAttribute intelligenceAttr;
    protected final CharacterAttribute agilityAttr;

    protected List<StatusEffect> statusEffects = new ArrayList<>();

    protected Character(int level, int strength, int intelligence, int agility) {
        this.level = level;
        this.strengthAttr = new CharacterAttribute(STRENGTH, strength);
        this.intelligenceAttr = new CharacterAttribute(INTELLIGENCE, intelligence);
        this.agilityAttr = new CharacterAttribute(AGILITY, agility);

        this.hp = getMaxHp();
        this.mana = getMaxMana();
    }

    public abstract CombatAction combat();

    public abstract void displayStats();

    public double getHp() {
        return Math.max(hp, 0.0);
    }

    public void dealDamage(double damage) {
        this.hp -= damage;
    }

    public void heal(double health) {
        this.hp += health;
        if (this.hp > this.getMaxHp()) {
            this.hp = getMaxHp();
        }
    }

    public double getMaxHp() {
        return BASE_HP + (HP_PER_LEVEL * (this.level - 1));
    }

    public boolean isDead() {
        return this.hp < 0.0;
    }

    public double getMana() {
        return this.mana;
    }

    public void regenMana(double mana) {
        this.mana += mana;
        if (this.mana > this.getMaxMana()) {
            this.mana = getMaxMana();
        }
    }

    public double getMaxMana() {
        return MANA_PER_INTELLIGENCE * intelligenceAttr.getValue();
    }

    public int getLevel() {
        return this.level;
    }

    public void levelUp() {
        this.level++;
        if (this instanceof Player player) {
            System.out.printf("%s has reached level %d%n", player.getName(), level);
        }
    }

    public int getStrength() {
        return strengthAttr.getValue();
    }

    public int getIntelligence() {
        return intelligenceAttr.getValue();
    }

    public int getAgility() {
        return agilityAttr.getValue();
    }

    public List<StatusEffect> getStatusEffects() {
        return this.statusEffects;
    }

    public Map<String, Double> applyEffects() {
        // Apply effects and remove the effects that have expired
        List<Effect> appliedStatusEffects = new ArrayList<>();
        for (StatusEffect effect : statusEffects) {
            if (effect.remove()) continue;
            appliedStatusEffects.add(effect.applyEffect());
        }
        statusEffects.removeIf(StatusEffect::remove);

        // Average the rate of change between all the effects
        double damage = 0.0;
        double strengthMultiplier = 0.0;
        double intelligenceMultiplier = 0.0;
        double agilityMultiplier = 0.0;

        int strengthMultiplierCounter = 0;
        int intelligenceMultiplierCounter = 0;
        int agilityMultiplierCounter = 0;
        for (Effect appliedEffect : appliedStatusEffects) {
            damage += appliedEffect.damage();

            if (appliedEffect.strength() > 0.0) {
                strengthMultiplier += appliedEffect.strength();
                strengthMultiplierCounter++;
            }

            if (appliedEffect.intelligence() > 0.0) {
                intelligenceMultiplier += appliedEffect.intelligence();
                intelligenceMultiplierCounter++;
            }

            if (appliedEffect.agility() > 0.0) {
                agilityMultiplier += appliedEffect.agility();
                agilityMultiplierCounter++;
            }
        }

        // Apply the averaged effects onto attributes
        if (damage > 0.0) dealDamage(damage);
        HashMap<String, Double> appliedAttributes = new HashMap<>();
        appliedAttributes.put(STRENGTH, getStrength() * strengthMultiplierCounter > 0 ?
                strengthMultiplier / strengthMultiplierCounter : 1.0);
        appliedAttributes.put(INTELLIGENCE, getIntelligence() * intelligenceMultiplierCounter > 0 ?
                intelligenceMultiplier / intelligenceMultiplierCounter : 1.0);
        appliedAttributes.put(AGILITY, getAgility() * agilityMultiplierCounter > 0 ?
                agilityMultiplier / agilityMultiplierCounter : 1.0);
        return appliedAttributes;
    }

    public static int[] generateStats(int min, int bound, int size) {
        int[] genStats = new int[size];
        for(int i = 0; i < size; i++) {
            genStats[i] = Game.getRandom().nextInt(min, bound);
        }
        return genStats;
    }
}
