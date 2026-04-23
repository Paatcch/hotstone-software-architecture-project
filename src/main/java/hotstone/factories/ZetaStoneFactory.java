package hotstone.factories;

import hotstone.variants.deckStrategy.DeckStrategy;
import hotstone.variants.deckStrategy.SpanishDeckStrategy;
import hotstone.variants.heroStrategy.BabyHeroStrategy;
import hotstone.variants.heroStrategy.HeroStrategy;
import hotstone.variants.manaStrategy.Constant3ManaStrategy;
import hotstone.variants.manaStrategy.ManaStrategy;
import hotstone.variants.winnerStrategy.*;

public class ZetaStoneFactory implements HotStoneFactory{

    @Override
    public ManaStrategy createManaStrategy() {
        return new Constant3ManaStrategy();
    }

    @Override
    public WinnerStategy createWinnerStrategy() {
        return new AlternatingWinnerStrategy(new FieldWinnerStrategy(), new HeroHealthWinnerStategy());
    }

    @Override
    public DeckStrategy createDeckStrategy() {
        return new SpanishDeckStrategy();
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