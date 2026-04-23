package TestStub;

import hotstone.framework.*;
import hotstone.observer.GameObserver;
import hotstone.observer.Observable;
import hotstone.standard.GameConstants;
import hotstone.standard.StandardCard;
import hotstone.variants.EffectStategy.BrownRiceEffectStrategy;

import java.util.ArrayList;


public class GameTestSpy implements MutableGame {
    String lastMethodCall = "No method called yet";
    ArrayList<String> allMethodsCalled = new ArrayList<>();
    Effect brownRiceStrategy = new BrownRiceEffectStrategy();

    public String getLastMethodCall(){
        return lastMethodCall;
    }
    public ArrayList<String> getAllMethodsCalled() {return allMethodsCalled;}

    int fieldSizeInt = 4; //More than 1, so we only have to change this when we test for no effect happening when eligible minion is in field

    public void setFieldSizeInt(int newFieldSizeInt) {
        fieldSizeInt = newFieldSizeInt;
    }

    @Override
    public void changeHeroHealth(Player who, int amount) {
        lastMethodCall = "changeHeroHealth " + who + " " + amount;
    }

    @Override
    public void changeMinionAttackPower(MutableCard card, int amount) {
        lastMethodCall = "changeMinionAttackPower " + card.getName() + " " + amount;
        allMethodsCalled.add(0, lastMethodCall);
    }

    @Override
    public void drawCard(Player who) {
        lastMethodCall = "drawCard " + who;
        allMethodsCalled.add(0, lastMethodCall);
    }


    @Override
    public void removeMinionIfHealthIsBelowOne(MutableCard card) {
        lastMethodCall = "removeMinionIfHealthIsBelowOne " + card.getName();
        allMethodsCalled.add(0, lastMethodCall);
    }

    @Override
    public void changeMinionHealth(MutableCard card, int amount) {
        lastMethodCall = "changeMinionHealth " + card.getName() + " " + amount;
        allMethodsCalled.add(0, lastMethodCall);

    }

    @Override
    public Player getPlayerInTurn() {
        return Player.FINDUS; //Fake it ???????
    }

    @Override
    public Hero getHero(Player who) {
        return null;
    }

    @Override
    public Player getWinner() {
        return null;
    }

    @Override
    public int getTurnNumber() {
        return 0;
    }

    @Override
    public int getDeckSize(Player who) {
        return 0;
    }

    @Override
    public Card getCardInHand(Player who, int indexInHand) {
        return null;
    }

    @Override
    public Iterable<? extends Card> getHand(Player who) {
        return null;
    }

    @Override
    public int getHandSize(Player who) {
        return 0;
    }

    @Override
    public Card getCardInField(Player who, int indexInField) {
        return new StandardCard(GameConstants.BROWN_RICE_CARD, 1,1,1,who, brownRiceStrategy, "Deal 1 damage to opponent hero.");
    }

    @Override
    public Iterable<? extends Card> getField(Player who) {
        return null;
    }

    @Override
    public int getFieldSize(Player who) {
        lastMethodCall = "getFieldSize " + who;
        allMethodsCalled.add(0, lastMethodCall);
        return fieldSizeInt;
    }

    @Override
    public void endTurn() {

    }

    @Override
    public Status playCard(Player who, Card card, int atIndex) {
        return null;
    }

    @Override
    public Status attackCard(Player playerAttacking, Card attackingCard, Card defendingCard) {
        return null;
    }

    @Override
    public Status attackHero(Player playerAttacking, Card attackingCard) {
        return null;
    }

    @Override
    public Status usePower(Player who) {
        return null;
    }

    @Override
    public void addObserver(GameObserver observer) {

    }
}
