package hotstone.factories;

import hotstone.variants.deckStrategy.DeckStrategy;
import hotstone.variants.deckStrategy.EffectDishDeckStrategy;
import hotstone.variants.heroStrategy.BabyHeroStrategy;
import hotstone.variants.heroStrategy.HeroStrategy;
import hotstone.variants.manaStrategy.Constant5ManaStrategy;
import hotstone.variants.manaStrategy.ManaStrategy;
import hotstone.variants.winnerStrategy.FindusWinnerStrategy;
import hotstone.variants.winnerStrategy.WinnerStategy;

public class EtaStoneFactory implements HotStoneFactory{
    @Override
    public ManaStrategy createManaStrategy() { return new Constant5ManaStrategy();}

    @Override
    public WinnerStategy createWinnerStrategy() {
        return new FindusWinnerStrategy();
    }

    @Override
    public DeckStrategy createDeckStrategy() {
        return new EffectDishDeckStrategy();
    }

    @Override
    public HeroStrategy createHeroStrategyFindus() {
        return new BabyHeroStrategy();
    }

    @Override
    public HeroStrategy createHeroStrategyPeddersen() {
        return new BabyHeroStrategy();
    }
}
