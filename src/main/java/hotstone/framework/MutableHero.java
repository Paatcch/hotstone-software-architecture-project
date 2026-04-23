package hotstone.framework;

import hotstone.standard.StandardHotStoneGame;
import hotstone.variants.heroStrategy.HeroStrategy;
import hotstone.variants.manaStrategy.ManaStrategy;

public interface MutableHero extends Hero{
    void resetMana(ManaStrategy manaStrategy, MutableGame game);
    boolean changeMana(int delta);
    void takeDamage(int amount);
    HeroStrategy getHeroStrategy(); //This is an accessor method, which would fit better in the Hero Interface, but we aren't allowed to add to that interface
}
