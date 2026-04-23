package hotstone.broker.client;

import frds.broker.ClientProxy;
import frds.broker.Requestor;
import hotstone.broker.common.OperationNames;
import hotstone.framework.Card;
import hotstone.framework.Player;

public class CardClientProxy implements Card, ClientProxy {
    Requestor requestor;
    String cardId;

    public CardClientProxy(Requestor requestor, String cardId) {
        this.requestor = requestor;
        this.cardId = cardId;
    }

    @Override
    public String getName() {
        String name = requestor.sendRequestAndAwaitReply(cardId, OperationNames.CARD_GET_NAME, String.class);
        return name;
    }

    @Override
    public int getManaCost() {
        int manaCost = requestor.sendRequestAndAwaitReply(cardId, OperationNames.CARD_GET_MANA_COST, Integer.class);
        return manaCost;
    }

    @Override
    public int getAttack() {
        int attack = requestor.sendRequestAndAwaitReply(cardId, OperationNames.CARD_GET_ATTACK, Integer.class);
        return attack;
    }

    @Override
    public int getHealth() {
        int health = requestor.sendRequestAndAwaitReply(cardId, OperationNames.CARD_GET_HEALTH, Integer.class);
        return health;
    }

    @Override
    public boolean isActive() {
        boolean isActive = requestor.sendRequestAndAwaitReply(cardId, OperationNames.CARD_IS_ACTIVE, Boolean.class);
        return isActive;
    }

    @Override
    public Player getOwner() {
        Player owner = requestor.sendRequestAndAwaitReply(cardId, OperationNames.CARD_GET_OWNER, Player.class);
        return owner;
    }

    @Override
    public String getEffectDescription() {
        String effectDescription = requestor.sendRequestAndAwaitReply(cardId, OperationNames.CARD_GET_EFFECT_DESCRIPTION, String.class);
        return effectDescription;
    }

    @Override
    public String getId() {
        return cardId;
    }
}
