package hotstone.factories;

import hotstone.variants.deckStrategy.DeckStrategy;
import hotstone.variants.deckStrategy.SpanishDeckStrategy;
import hotstone.variants.heroStrategy.FrenchChefHeroStrategy;
import hotstone.variants.heroStrategy.HeroStrategy;
import hotstone.variants.heroStrategy.ItalianChefHeroStrategy;
import hotstone.variants.MinionDecisionStrategy.MinionDecisionStrategy;
import hotstone.variants.MinionDecisionStrategy.RandomMinionDecisionStrategy;
import hotstone.variants.manaStrategy.Constant3ManaStrategy;
import hotstone.variants.manaStrategy.ManaStrategy;
import hotstone.variants.winnerStrategy.FindusWinnerStrategy;
import hotstone.variants.winnerStrategy.WinnerStategy;

public class EpsilonStoneFactory implements HotStoneFactory {

    private MinionDecisionStrategy minionDecisionStrategy = new RandomMinionDecisionStrategy();

    @Override
    public ManaStrategy createManaStrategy() { return new Constant3ManaStrategy();}

    @Override
    public WinnerStategy createWinnerStrategy() {
        return new FindusWinnerStrategy();
    }

    @Override
    public DeckStrategy createDeckStrategy() {
        return new SpanishDeckStrategy();
    }

    @Override
    public HeroStrategy createHeroStrategyFindus() {
        return new FrenchChefHeroStrategy(minionDecisionStrategy);
    }

    @Override
    public HeroStrategy createHeroStrategyPeddersen() {
        return new ItalianChefHeroStrategy(minionDecisionStrategy);
    }

}
