package hotstone.standard;

import hotstone.variants.heroStrategy.HeroStrategy;
import hotstone.variants.heroStrategy.RandomChefHeroStrategy;
import org.junit.jupiter.api.Test;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

public class TestRandomChefHeroStrategy {

    @Test
    public void shouldHaveAllChefHeroStrategiesAppearFor1000Games(){
        int danishChefCount = 0;
        int frenchChefCount = 0;
        int italianChefCount = 0;
        int thaiChefCount = 0;

        for(int i = 0; i < 1000 ; i++){
            HeroStrategy randomChefHeroStrategy = new RandomChefHeroStrategy();
            if(randomChefHeroStrategy.getType().equals(GameConstants.DANISH_CHEF_HERO_TYPE)){
                danishChefCount++;
            }
            else if(randomChefHeroStrategy.getType().equals(GameConstants.FRENCH_CHEF_HERO_TYPE)){
                frenchChefCount++;
            }
            else if(randomChefHeroStrategy.getType().equals(GameConstants.ITALIAN_CHEF_HERO_TYPE)){
                italianChefCount++;
            }
            else if(randomChefHeroStrategy.getType().equals(GameConstants.THAI_CHEF_HERO_TYPE)){
                thaiChefCount++;
            }
        }
        int totalCount = danishChefCount + frenchChefCount + italianChefCount + thaiChefCount;
        assertThat(totalCount, is(1000));
        assertThat(danishChefCount, greaterThanOrEqualTo(1));
        assertThat(frenchChefCount, greaterThanOrEqualTo(1));
        assertThat(italianChefCount, greaterThanOrEqualTo(1));
        assertThat(thaiChefCount, greaterThanOrEqualTo(1));
    }

}
