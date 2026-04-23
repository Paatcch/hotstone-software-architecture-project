package hotstone.broker.client;

import frds.broker.ClientProxy;
import frds.broker.Requestor;
import hotstone.broker.common.OperationNames;
import hotstone.framework.Hero;
import hotstone.framework.Player;

public class HeroClientProxy implements Hero, ClientProxy {
    Requestor requestor;
    String heroId;

    public HeroClientProxy(Requestor requestor, String heroId) {
        this.requestor = requestor;
        this.heroId = heroId;
    }

    @Override
    public int getMana() {
        int mana = requestor.sendRequestAndAwaitReply(heroId, OperationNames.HERO_GET_MANA, Integer.class);
        return mana;
    }

    @Override
    public int getHealth() {
        int health = requestor.sendRequestAndAwaitReply(heroId, OperationNames.HERO_GET_HEALTH, Integer.class);
        return health;
    }

    @Override
    public boolean canUsePower() {
        boolean canUsePower = requestor.sendRequestAndAwaitReply(heroId, OperationNames.HERO_CAN_USE_POWER, Boolean.class);
        return canUsePower;
    }

    @Override
    public String getType() {
        String type = requestor.sendRequestAndAwaitReply(heroId, OperationNames.HERO_GET_TYPE, String.class);
        return type;
    }

    @Override
    public Player getOwner() {
        Player owner = requestor.sendRequestAndAwaitReply(heroId, OperationNames.HERO_GET_OWNER, Player.class);
        return owner;
    }

    @Override
    public String getEffectDescription() {
        String effectDescription = requestor.sendRequestAndAwaitReply(heroId, OperationNames.HERO_GET_EFFECT_DESCRIPTION, String.class);
        return effectDescription;
    }

    @Override
    public String getId() {
        return heroId;
    }
}
