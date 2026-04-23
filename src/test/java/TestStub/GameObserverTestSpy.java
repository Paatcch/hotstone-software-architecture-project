package TestStub;
import hotstone.framework.Card;
import hotstone.framework.Player;
import hotstone.observer.GameObserver;

import java.util.ArrayList;

public class GameObserverTestSpy implements GameObserver {
    private String lastMethodCalled = "No method called yet";
    private ArrayList<String> allMethodsCalled = new ArrayList<>();

    public String getLastMethodCalled(){
        return lastMethodCalled;
    }

    public ArrayList getAllMethodCalled(){
        return allMethodsCalled;
    }

    @Override
    public void onPlayCard(Player who, Card card, int atIndex) {
        lastMethodCalled = "onPlayCard(" + who + ", " + card.getName() + ", " + atIndex + ")";
        allMethodsCalled.add(0, lastMethodCalled);
    }

    @Override
    public void onChangeTurnTo(Player playerBecomingActive) {
        lastMethodCalled = "onChangeTurnTo(" + playerBecomingActive + ")";
        allMethodsCalled.add(0, lastMethodCalled);
    }

    @Override
    public void onAttackCard(Player playerAttacking, Card attackingCard, Card defendingCard) {
        lastMethodCalled = "onAttackCard(" + playerAttacking + ", " + attackingCard.getName() + ", " + defendingCard.getName() + ")";
        allMethodsCalled.add(0, lastMethodCalled);
    }

    @Override
    public void onAttackHero(Player playerAttacking, Card attackingCard) {
        lastMethodCalled = "onAttackHero(" + playerAttacking + ", " + attackingCard.getName() + ")";
        allMethodsCalled.add(0, lastMethodCalled);
    }

    @Override
    public void onUsePower(Player who) {
        lastMethodCalled = "onUsePower(" + who + ")";
        allMethodsCalled.add(0, lastMethodCalled);
    }

    @Override
    public void onCardDraw(Player who, Card drawnCard) {
        lastMethodCalled = "onCardDraw(" + who + ", " + drawnCard.getName() + ")";
        allMethodsCalled.add(0, lastMethodCalled);
    }

    @Override
    public void onCardUpdate(Card card) {
        lastMethodCalled = "onCardUpdate(" + card.getName() + ")";
        allMethodsCalled.add(0, lastMethodCalled);
    }

    @Override
    public void onCardRemove(Player who, Card card) {
        lastMethodCalled = "onCardRemove(" + who + ", " + card.getName() + ")";
        allMethodsCalled.add(0, lastMethodCalled);
    }

    @Override
    public void onHeroUpdate(Player who) {
        lastMethodCalled = "onHeroUpdate(" + who + ")";
        allMethodsCalled.add(0, lastMethodCalled);
    }

    @Override
    public void onGameWon(Player playerWinning) {
        lastMethodCalled = "onGameWon(" + playerWinning + ")";
        allMethodsCalled.add(0, lastMethodCalled);
    }
}