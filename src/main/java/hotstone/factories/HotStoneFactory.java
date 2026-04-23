package hotstone.factories;

import hotstone.variants.deckStrategy.DeckStrategy;
import hotstone.variants.heroStrategy.HeroStrategy;
import hotstone.variants.manaStrategy.ManaStrategy;
import hotstone.variants.winnerStrategy.WinnerStategy;

public interface HotStoneFactory {
    ManaStrategy createManaStrategy();
    WinnerStategy createWinnerStrategy();
    DeckStrategy createDeckStrategy();
    HeroStrategy createHeroStrategyFindus();
    HeroStrategy createHeroStrategyPeddersen();
}
