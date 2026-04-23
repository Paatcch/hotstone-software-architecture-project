package hotstone.decorator;

import hotstone.framework.*;
import hotstone.observer.GameObserver;

public class TranscriptedDecoratorHotStoneGame implements MutableGame {
    MutableGame realGame;
    Status status;

    public TranscriptedDecoratorHotStoneGame(MutableGame mutableGame){
        realGame = mutableGame;
    }

    @Override
    public void changeHeroHealth(Player who, int amount) {
        realGame.changeHeroHealth(who, amount);
    }

    @Override
    public void changeMinionAttackPower(MutableCard card, int amount) {
        realGame.changeMinionAttackPower(card, amount);
    }

    @Override
    public void drawCard(Player who) {
        realGame.drawCard(who);
    }

    @Override
    public void removeMinionIfHealthIsBelowOne(MutableCard card) {
        realGame.removeMinionIfHealthIsBelowOne(card);
    }

    @Override
    public void changeMinionHealth(MutableCard card, int amount) {
        realGame.changeMinionHealth(card, amount);
    }

    @Override
    public Player getPlayerInTurn() {
        return realGame.getPlayerInTurn();
    }

    @Override
    public Hero getHero(Player who) {
        return realGame.getHero(who);
    }

    @Override
    public Player getWinner() {
        return realGame.getWinner();
    }

    @Override
    public int getTurnNumber() {
        return realGame.getTurnNumber();
    }

    @Override
    public int getDeckSize(Player who) {
        return realGame.getDeckSize(who);
    }

    @Override
    public Card getCardInHand(Player who, int indexInHand) {
        return realGame.getCardInHand(who, indexInHand);
    }

    @Override
    public Iterable<? extends Card> getHand(Player who) {
        return realGame.getHand(who);
    }

    @Override
    public int getHandSize(Player who) {
        return realGame.getHandSize(who);
    }

    @Override
    public Card getCardInField(Player who, int indexInField) {
        return realGame.getCardInField(who, indexInField);
    }

    @Override
    public Iterable<? extends Card> getField(Player who) {
        return realGame.getField(who);
    }

    @Override
    public int getFieldSize(Player who) {
        return realGame.getFieldSize(who);
    }

    @Override
    public void endTurn() {
        System.out.println(realGame.getPlayerInTurn() + " ended their turn");
        realGame.endTurn();
    }

    @Override
    public Status playCard(Player who, Card card, int atIndex) {
        status = realGame.playCard(who, card, atIndex);
        if(status.equals(Status.OK)){
            System.out.println(realGame.getPlayerInTurn() + " played " + card.getName() + " at index " + atIndex);
        }
        return status;
    }

    @Override
    public Status attackCard(Player playerAttacking, Card attackingCard, Card defendingCard) {
        status = realGame.attackCard(playerAttacking, attackingCard, defendingCard);
        if(status.equals(Status.OK)){
            System.out.println(realGame.getPlayerInTurn() + " attacked " + defendingCard.getName() + " with " + attackingCard.getName());
        }
        return status;
    }

    @Override
    public Status attackHero(Player playerAttacking, Card attackingCard) {
        status = realGame.attackHero(playerAttacking, attackingCard);
        if(status.equals(Status.OK)){
            System.out.println(playerAttacking+ " attacked " + Player.computeOpponent(playerAttacking) + "'s Hero with " + attackingCard.getName());
        }
        return status;
    }

    @Override
    public Status usePower(Player who) {
        status = realGame.usePower(who);
        if(status.equals(Status.OK)){
            System.out.println(who + " used Hero power");
        }
        return status;
    }

    @Override
    public void addObserver(GameObserver observer) {
        realGame.addObserver(observer);
    }
}
