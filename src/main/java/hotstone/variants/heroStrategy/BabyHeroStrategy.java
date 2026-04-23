package hotstone.variants.heroStrategy;

import hotstone.framework.MutableGame;
import hotstone.standard.GameConstants;
import hotstone.standard.StandardHotStoneGame;

public class BabyHeroStrategy implements HeroStrategy{
    @Override
    public String getType() {
        return GameConstants.BABY_HERO_TYPE;
    }

    @Override
    public String getEffectDescription() {
        return "Just Cute";
    }

    @Override
    public void usePower(MutableGame game) {
    }
}
