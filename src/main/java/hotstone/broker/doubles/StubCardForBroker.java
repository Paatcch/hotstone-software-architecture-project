package hotstone.broker.doubles;

import frds.broker.Servant;
import hotstone.framework.Card;
import hotstone.framework.Player;

public class StubCardForBroker implements Card, Servant {
    @Override
    public String getName() {
        return "some name";
    }

    @Override
    public int getManaCost() {
        return 33;
    }

    @Override
    public int getAttack() {
        return 44;
    }

    @Override
    public int getHealth() {
        return 55;
    }

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public Player getOwner() {
        return Player.PEDDERSEN;
    }

    @Override
    public String getEffectDescription() {
        return "some effect description";
    }

    @Override
    public String getId() {
        return "";
    }
}
