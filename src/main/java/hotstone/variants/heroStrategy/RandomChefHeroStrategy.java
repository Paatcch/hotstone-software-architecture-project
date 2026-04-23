package hotstone.variants.heroStrategy;

import hotstone.framework.MutableGame;
import hotstone.variants.MinionDecisionStrategy.MinionDecisionStrategy;
import hotstone.variants.MinionDecisionStrategy.RandomMinionDecisionStrategy;

import java.util.ArrayList;
import java.util.Random;

public class RandomChefHeroStrategy implements HeroStrategy{
    MinionDecisionStrategy minionDecisionStrategy = new RandomMinionDecisionStrategy();
    ArrayList<HeroStrategy> heroList = new ArrayList<>();
    HeroStrategy randomHeroStrategy;

    public RandomChefHeroStrategy(){
        heroList.add(new DanishChefHeroStrategy());
        heroList.add(new ThaiChefHeroStrategy());
        heroList.add(new FrenchChefHeroStrategy(minionDecisionStrategy));
        heroList.add(new ItalianChefHeroStrategy(minionDecisionStrategy));

        Random random = new Random();
        int randomIndex = random.nextInt(4);
        randomHeroStrategy = heroList.get(randomIndex);
    }

    @Override
    public String getType() {
        return randomHeroStrategy.getType();
    }

    @Override
    public String getEffectDescription() {
        return randomHeroStrategy.getEffectDescription();
    }

    @Override
    public void usePower(MutableGame game) {
        randomHeroStrategy.usePower(game);
    }
}
