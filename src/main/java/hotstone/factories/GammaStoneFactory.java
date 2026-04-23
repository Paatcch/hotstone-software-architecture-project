package hotstone.factories;

import hotstone.variants.deckStrategy.DeckStrategy;
import hotstone.variants.deckStrategy.SpanishDeckStrategy;
import hotstone.variants.heroStrategy.DanishChefHeroStrategy;
import hotstone.variants.heroStrategy.HeroStrategy;
import hotstone.variants.heroStrategy.ThaiChefHeroStrategy;
import hotstone.variants.manaStrategy.Constant3ManaStrategy;
import hotstone.variants.manaStrategy.ManaStrategy;
import hotstone.variants.winnerStrategy.FieldWinnerStrategy;
import hotstone.variants.winnerStrategy.WinnerStategy;

public class GammaStoneFactory implements HotStoneFactory {

    @Override
    public ManaStrategy createManaStrategy() { return new Constant3ManaStrategy();}

    @Override
    public WinnerStategy createWinnerStrategy() {
        return new FieldWinnerStrategy();
    }

    @Override
    public DeckStrategy createDeckStrategy() {
        return new SpanishDeckStrategy();
    }

    @Override
    public HeroStrategy createHeroStrategyFindus() {
        return new ThaiChefHeroStrategy();
    }

    @Override
    public HeroStrategy createHeroStrategyPeddersen() {
        return new DanishChefHeroStrategy();
    }
}

