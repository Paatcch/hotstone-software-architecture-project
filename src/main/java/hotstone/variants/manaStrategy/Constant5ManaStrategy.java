package hotstone.variants.manaStrategy;

import hotstone.framework.MutableGame;

public class Constant5ManaStrategy implements ManaStrategy{
    @Override
    public int calculateMana(MutableGame game) {
        return 5;
    }
}
