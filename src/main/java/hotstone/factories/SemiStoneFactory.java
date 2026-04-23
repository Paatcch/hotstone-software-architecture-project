package hotstone.factories;

import hotstone.variants.deckStrategy.DeckStrategy;
import hotstone.variants.deckStrategy.EffectDishDeckStrategy;
import hotstone.variants.heroStrategy.HeroStrategy;
import hotstone.variants.heroStrategy.RandomChefHeroStrategy;
import hotstone.variants.manaStrategy.ManaStrategy;
import hotstone.variants.manaStrategy.TurnBasedManaStrategy;
import hotstone.variants.winnerStrategy.HeroHealthWinnerStategy;
import hotstone.variants.winnerStrategy.WinnerStategy;

public class SemiStoneFactory implements HotStoneFactory{
    @Override
    public ManaStrategy createManaStrategy() {
        return new TurnBasedManaStrategy();
    }

    @Override
    public WinnerStategy createWinnerStrategy() {
        return new HeroHealthWinnerStategy();
    }

    @Override
    public DeckStrategy createDeckStrategy() {
        return new EffectDishDeckStrategy();
    }

    @Override
    public HeroStrategy createHeroStrategyFindus() {
        return new RandomChefHeroStrategy();
    }

    @Override
    public HeroStrategy createHeroStrategyPeddersen() {
        return new RandomChefHeroStrategy();
    }
}
