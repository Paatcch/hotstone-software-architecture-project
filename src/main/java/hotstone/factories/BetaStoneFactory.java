package hotstone.factories;

import hotstone.variants.deckStrategy.DeckStrategy;
import hotstone.variants.deckStrategy.SpanishDeckStrategy;
import hotstone.variants.heroStrategy.BabyHeroStrategy;
import hotstone.variants.heroStrategy.HeroStrategy;
import hotstone.variants.manaStrategy.ManaStrategy;
import hotstone.variants.manaStrategy.TurnBasedManaStrategy;
import hotstone.variants.winnerStrategy.HeroHealthWinnerStategy;
import hotstone.variants.winnerStrategy.WinnerStategy;

public class BetaStoneFactory implements HotStoneFactory {

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
