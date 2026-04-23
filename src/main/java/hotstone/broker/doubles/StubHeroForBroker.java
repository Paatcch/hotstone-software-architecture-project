package hotstone.broker.doubles;

import frds.broker.Servant;
import hotstone.framework.Hero;
import hotstone.framework.Player;

public class StubHeroForBroker implements Hero, Servant {

    @Override
    public int getMana() {
        return 400;
    }

    @Override
    public int getHealth() {
        return 1;
    }

    @Override
    public boolean canUsePower() {
        return true;
    }

    @Override
    public String getType() {
        return "some type";
    }

    @Override
    public Player getOwner() {
        return Player.PEDDERSEN;
    }

    @Override
    public String getEffectDescription() {
        return "some effect";
    }

    @Override
    public String getId() {
        return "";
    }
}
