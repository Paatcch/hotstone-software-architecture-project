package hotstone.standard;

import hotstone.framework.Card;
import hotstone.framework.Effect;
import hotstone.framework.MutableCard;
import hotstone.framework.Player;

import java.util.UUID;

public class StandardCard implements Card, MutableCard {
    private String name;
    private int manaCost;
    private int attack;
    private int health;
    private boolean active;
    private Player owner;
    private Effect effect;
    private String effectDescription;
    private String id;

    public StandardCard(String name, int manaCost, int attack, int health, Player owner, Effect effect, String effectDescription){
        this.name = name;
        this.manaCost = manaCost;
        this.attack = attack;
        this.health = health;
        active = false;
        this.owner = owner;
        this.effect = effect;
        this.effectDescription = effectDescription;
        id = UUID.randomUUID().toString();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getManaCost() {
        return manaCost;
    }

    @Override
    public int getAttack() {
        return attack;
    }

    public void takeDamage(int damage){
        health -= damage;
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean verdict){ //HVAD SKAL VI KALDE VERDICT????
        active = verdict;
    }

    @Override
    public Player getOwner() {
        return owner;
    }

    @Override
    public String getEffectDescription() {
        return effectDescription;
    }

    public void changeAttackPower(int amount){
        attack += amount;
        if(attack < 0){
            attack = 0;
        }
    }

    public Effect getEffect(){
        return effect;
    }

    @Override
    public String getId() {
        return id;
    }
}
