/*
 * Copyright (C) 2022 - 2025. Henrik Bærbak Christensen, Aarhus University.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package hotstone.standard;

import hotstone.adapter.EffectWizardAdapter;
import hotstone.factories.HotStoneFactory;
import hotstone.framework.*;
import hotstone.observer.GameObserver;
import hotstone.observer.ObserverHandler;
import hotstone.variants.deckStrategy.DeckStrategy;
import hotstone.variants.heroStrategy.HeroStrategy;
import hotstone.variants.manaStrategy.ManaStrategy;
import hotstone.variants.winnerStrategy.WinnerStategy;

import java.util.*;

/**
 * This is the 'temporary test stub' in TDD
 * terms: the initial empty but compilable implementation
 * of the game interface.
 * <p>
 * It already includes a bit of FAKE-IT code for the first
 * test case about hand management and player in turn.
 * <p>
 * Start solving the AlphaStone exercise by
 * following the TDD rhythm: pick a one-step-test
 * from your test list, quickly add a test,
 * run it to see it fail, and then modify this
 * implementing class (and supporting classes)
 * to make your test case run. Refactor and repeat.
 * <p>
 * While this is the implementation of Game for
 * the AlphaStone game, you will constantly
 * refactor it over the course of the exercises
 * to become the 'core implementation' which will
 * enable a lot of game variants. This is also
 * why it is not called 'AlphaGame'.
 */

public class StandardHotStoneGame implements Game, MutableGame {
    private Player currentPlayer;
    private Map<Player, MutableHero> heroMap;
    private Map<Player, List<MutableCard>> handMap;
    private Map<Player, List<MutableCard>> deckMap;
    private Map<Player, List<MutableCard>> fieldMap;

    private int turnNumber;
    private boolean haveUsedPowerThisTurn = false;

    private ObserverHandler observerHandler;

    private WinnerStategy winnerStatety;
    private ManaStrategy manaStrategy;
    private HeroStrategy heroStrategyFindus;
    private HeroStrategy heroStrategyPeddersen;
    private DeckStrategy deckStrategy;

    private EffectWizardAdapter effectWizardAdapter = new EffectWizardAdapter(this);

    public StandardHotStoneGame(HotStoneFactory factory) {
        observerHandler = new ObserverHandler();

        this.winnerStatety = factory.createWinnerStrategy();
        this.manaStrategy = factory.createManaStrategy();
        this.heroStrategyFindus = factory.createHeroStrategyFindus();
        this.heroStrategyPeddersen = factory.createHeroStrategyPeddersen();
        this.deckStrategy = factory.createDeckStrategy();
        currentPlayer = Player.FINDUS;
        turnNumber = 1;
        initiateDecks();
        initiateFields();
        initiateHeros();
        initiateHands();
    }

    private void initiateHeros() {
        MutableHero heroFindus;
        MutableHero heroPeddersen;

        heroFindus = new StandardHero(manaStrategy.calculateMana(this), GameConstants.HERO_MAX_HEALTH, Player.FINDUS, GameConstants.HERO_POWER_COST, heroStrategyFindus);
        heroPeddersen = new StandardHero(manaStrategy.calculateMana(this), GameConstants.HERO_MAX_HEALTH, Player.PEDDERSEN, GameConstants.HERO_POWER_COST, heroStrategyPeddersen);

        heroMap = new HashMap<>();
        heroMap.put(Player.FINDUS, heroFindus);
        heroMap.put(Player.PEDDERSEN, heroPeddersen);
    }

    public void initiateDecks() {
        List<MutableCard> deckListFindus = deckStrategy.buildDeck(Player.FINDUS);
        List<MutableCard> deckListPeddersen = deckStrategy.buildDeck(Player.PEDDERSEN);

        deckMap = new HashMap<>();
        deckMap.put(Player.FINDUS, deckListFindus);
        deckMap.put(Player.PEDDERSEN, deckListPeddersen);
    }

    public void initiateFields() {
        List<MutableCard> fieldListFindus = new ArrayList<>();
        List<MutableCard> fieldListPeddersen = new ArrayList<>();

        fieldMap = new HashMap<>();
        fieldMap.put(Player.FINDUS, fieldListFindus);
        fieldMap.put(Player.PEDDERSEN, fieldListPeddersen);
    }

    public void initiateHands() { //Should use draw card method instead of adding

        List<MutableCard> handListFindus = new ArrayList<>();
        List<MutableCard> handListPeddersen = new ArrayList<>();

        handMap = new HashMap<>();
        handMap.put(Player.FINDUS, handListFindus);
        handMap.put(Player.PEDDERSEN, handListPeddersen);

        drawCard(Player.FINDUS);
        drawCard(Player.FINDUS);
        drawCard(Player.FINDUS);

        drawCard(Player.PEDDERSEN);
        drawCard(Player.PEDDERSEN);
        drawCard(Player.PEDDERSEN);
    }

    /**
     * Draw the top card from current players deck
     */
    @Override
    public void drawCard(Player who) {
        //Check if the players deck is empty. If it is, then they take two damage
        //Draw the top card from players deck
        //remove the drawn card from players deck
        //add drawn card to players hand

        if (!deckMap.get(who).isEmpty()) {
            MutableCard cardDrawn = deckMap.get(who).get(0);
            deckMap.get(who).remove(0);
            handMap.get(who).add(0, cardDrawn);
            observerHandler.notifyCardDraw(who, cardDrawn);
        } else {
            heroMap.get(who).takeDamage(2);
        }
    }

    @Override
    public Player getPlayerInTurn() {
        return currentPlayer;
    }

    @Override
    public Hero getHero(Player who) {
        return heroMap.get(who);
    }

    @Override
    public Player getWinner() {
        return winnerStatety.checkWinner(this);
    }

    public void checkForWinnerAndNotify(){
        Player winner = getWinner();
        if(winner != null){
            observerHandler.notifyGameWon(winner);
        }
    }

    @Override
    public int getTurnNumber() {
        return turnNumber;
    }

    @Override
    public int getDeckSize(Player who) {
        return deckMap.get(who).size();
    }

    @Override
    public Card getCardInHand(Player who, int indexInHand) {
        return handMap.get(who).get(indexInHand);
    }

    @Override
    public Iterable<? extends Card> getHand(Player who) {
        return handMap.get(who);
    }

    @Override
    public int getHandSize(Player who) {
        return handMap.get(who).size();
    }

    @Override
    public Card getCardInField(Player who, int indexInField) {
        return fieldMap.get(who).get(indexInField);
    }

    @Override
    public Iterable<? extends Card> getField(Player who) {
        return fieldMap.get(who);
    }

    @Override
    public int getFieldSize(Player who) {
        return fieldMap.get(who).size();
    }

    @Override
    public void endTurn() {
        if (currentPlayer.equals(Player.FINDUS)) { //Check who the current player is
            currentPlayer = Player.PEDDERSEN; //change current player to Peddersen
        } else {
            currentPlayer = Player.FINDUS; //change current player to Findus
        }
        for (MutableCard card : fieldMap.get(currentPlayer)) { //The current players minions become active again
            card.setActive(true);
            observerHandler.notifyCardUpdate(card);
        }
        turnNumber++;
        haveUsedPowerThisTurn = false;
        drawCard(currentPlayer); //current player draws a card
        heroMap.get(currentPlayer).resetMana(manaStrategy, this);

        checkForWinnerAndNotify();
        observerHandler.notifyHeroUpdate(currentPlayer);
        observerHandler.notifyChangeTurnTo(currentPlayer);
    }

    @Override
    public Status playCard(Player who, Card card, int atIndex) {
        MutableCard mutableCard = (MutableCard) card;

        Status status = isPlayCardValid(who, card, atIndex);

        if (status.equals(Status.OK)){
            heroMap.get(who).changeMana(-mutableCard.getManaCost()); //changeMana gets a negative argument, since we reduce the players mana
            handMap.get(who).remove(mutableCard);
            mutableCard.getEffect().useEffect(this); //Does the effect before adding the card to the field. This way the effect does not affect the played card
            fieldMap.get(who).add(atIndex, mutableCard);

            observerHandler.notifyHeroUpdate(who); //As mana is used
            observerHandler.notifyPlayCard(who, card, atIndex);
        }
        return status;
    }

    public Status isPlayCardValid(Player who, Card card, int atIndex){
        if (!who.equals(currentPlayer)) {
            return Status.NOT_PLAYER_IN_TURN;
        }
        if (!who.equals(card.getOwner())) {
            return Status.NOT_OWNER;
        }
        if (heroMap.get(who).getMana() < card.getManaCost()){
            return Status.NOT_ENOUGH_MANA;
        }
        return Status.OK;
    }

    @Override
    public Status attackCard(Player playerAttacking, Card attackingCard, Card defendingCard) {
        MutableCard defendingMutableCard = (MutableCard) defendingCard;
        MutableCard attackingMutableCard = (MutableCard) attackingCard;

        Status status = checkIfAttackIsValid(playerAttacking, attackingMutableCard, defendingMutableCard);
        if (status != Status.OK) {
            return status;
        }

        resolveAttack(attackingMutableCard, defendingMutableCard);

        observerHandler.notifyAttackCard(playerAttacking, attackingCard, defendingCard);
        return Status.OK;
    }

    public void resolveAttack(MutableCard attackingMutableCard, MutableCard defendingMutableCard){
        changeMinionHealth(defendingMutableCard, -attackingMutableCard.getAttack());
        changeMinionHealth(attackingMutableCard, -defendingMutableCard.getAttack());

        attackingMutableCard.setActive(false);

        observerHandler.notifyCardUpdate(attackingMutableCard); //Update af to set the card inactive
    }

    public Status checkIfAttackIsValid(Player playerAttacking, Card attackingCard, Card defendingCard) {
        if (!playerAttacking.equals(currentPlayer)) {
            return Status.NOT_PLAYER_IN_TURN;
        }
        if (!playerAttacking.equals(attackingCard.getOwner())) {
            return Status.NOT_OWNER;
        }
        if (attackingCard.getOwner().equals(defendingCard.getOwner())) {
            return Status.ATTACK_NOT_ALLOWED_ON_OWN_MINION;
        }
        if (!attackingCard.isActive()) {
            return Status.ATTACK_NOT_ALLOWED_FOR_NON_ACTIVE_MINION;
        }

        return Status.OK;
    }

    @Override
    public Status attackHero(Player playerAttacking, Card attackingCard) {
        if (!playerAttacking.equals(currentPlayer)) {
            return Status.NOT_PLAYER_IN_TURN;
        }
        if (!playerAttacking.equals(attackingCard.getOwner())) {
            return Status.NOT_OWNER;
        }

        MutableCard aCard = (MutableCard) attackingCard;
        if (aCard.isActive()) {
            Player opponentPlayer = Player.computeOpponent(playerAttacking);
            changeHeroHealth(opponentPlayer, -aCard.getAttack());
            aCard.setActive(false);

            observerHandler.notifyCardUpdate(attackingCard);
            observerHandler.notifyAttackHero(playerAttacking, attackingCard);
            return Status.OK;
        }
        return Status.ATTACK_NOT_ALLOWED_FOR_NON_ACTIVE_MINION;
    }

    @Override
    public Status usePower(Player who) {
        if (!who.equals(currentPlayer)) {
            return Status.NOT_PLAYER_IN_TURN;
        }

        if(haveUsedPowerThisTurn){
            return Status.POWER_USE_NOT_ALLOWED_TWICE_PR_ROUND;
        }

        if (heroMap.get(who).changeMana(-GameConstants.HERO_POWER_COST)) {
            heroMap.get(who).getHeroStrategy().usePower(this);
            observerHandler.notifyHeroUpdate(who);
            observerHandler.notifyUsePower(who);
            haveUsedPowerThisTurn = true;
            return Status.OK;
        }

        return Status.NOT_ENOUGH_MANA;
    }

    public int getHeroHealth(Player who) { //A helping method to reduce coupling
        return getHero(who).getHealth();
    }

    @Override
    public void changeHeroHealth(Player who, int amount) {
        MutableHero hero = (MutableHero) getHero(who);
        hero.takeDamage(-amount);

        checkForWinnerAndNotify();
        observerHandler.notifyHeroUpdate(who);
    }

    @Override
    public void changeMinionAttackPower(MutableCard card, int amount){
        card.changeAttackPower(amount);

        observerHandler.notifyCardUpdate(card);
    }

    @Override
    public void addObserver(GameObserver observer) {
        observerHandler.addObserver(observer);
    }

    @Override
    public void removeMinionIfHealthIsBelowOne(MutableCard card){
        if (card.getHealth() <= 0) {
            fieldMap.get(card.getOwner()).remove(card);

            checkForWinnerAndNotify();
            observerHandler.notifyCardRemove(card.getOwner(), card);
        }
    }

    @Override
    public void changeMinionHealth(MutableCard card, int amount){
        card.takeDamage(-amount);
        removeMinionIfHealthIsBelowOne(card);

        observerHandler.notifyCardUpdate(card);
    }
}
