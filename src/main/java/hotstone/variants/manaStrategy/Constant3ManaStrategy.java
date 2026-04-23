package hotstone.variants.manaStrategy;

import hotstone.framework.MutableGame;

public class Constant3ManaStrategy implements ManaStrategy{
    @Override
    public int calculateMana(MutableGame game) {
        return 3;
    }
}
