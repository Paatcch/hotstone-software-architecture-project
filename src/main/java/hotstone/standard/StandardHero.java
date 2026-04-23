package hotstone.standard;

import hotstone.framework.Hero;
import hotstone.framework.MutableGame;
import hotstone.framework.MutableHero;
import hotstone.framework.Player;
import hotstone.variants.heroStrategy.HeroStrategy;
import hotstone.variants.manaStrategy.ManaStrategy;

import java.util.UUID;

public class StandardHero implements Hero, MutableHero {

    private int mana;
    private int health;
    private final String type;
    private final Player owner;
    private final String effectDescription;
    private final int powerCost;
    private HeroStrategy heroStrategy;
    private String id;

    public StandardHero(int mana, int health, Player owner, int powerCost, HeroStrategy heroStrategy){
        this.mana = mana;
        this.health = health;
        this.owner = owner;
        this.powerCost = powerCost;
        this.heroStrategy = heroStrategy;
        this.type = heroStrategy.getType();
        this.effectDescription = heroStrategy.getEffectDescription();
        id = UUID.randomUUID().toString();
    }

    @Override
    public int getMana() {
        return mana;
    }

    @Override
    public void resetMana(ManaStrategy manaStrategy, MutableGame game){
        mana = manaStrategy.calculateMana(game);
    }

    @Override
    public boolean changeMana(int delta){
        if(Math.abs(delta) <= mana) {
            mana += delta;
            return true;
        }
        return false;
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public void takeDamage(int amount){
        health -= amount;
    }

    @Override
    public boolean canUsePower() {
        return true;
    } //Fake It

    @Override
    public String getType() {
        return type;
    }

    @Override
    public Player getOwner() {
        return owner;
    }

    @Override
    public String getEffectDescription() {
        return effectDescription;
    }

    public HeroStrategy getHeroStrategy(){
        return heroStrategy;
    }

    @Override
    public String getId() {
        return id;
    }
}
