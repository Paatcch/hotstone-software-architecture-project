package hotstone.variants.heroStrategy;

import hotstone.framework.MutableGame;

public interface HeroStrategy {
    String getType();
    String getEffectDescription();
    void usePower(MutableGame game);
}
