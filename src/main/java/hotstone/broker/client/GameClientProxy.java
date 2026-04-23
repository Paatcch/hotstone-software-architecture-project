/*
 * Copyright (C) 2022 - 2024. Henrik Bærbak Christensen, Aarhus University.
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

package hotstone.broker.client;

import com.google.gson.reflect.TypeToken;
import frds.broker.ClientProxy;
import frds.broker.Requestor;
import hotstone.broker.common.OperationNames;
import hotstone.framework.*;
import hotstone.observer.GameObserver;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO: Template/starter code for your ClientProxy of Game.
 */
public class GameClientProxy implements Game, ClientProxy {
    Requestor requestor;
    String gameId = "one-game";

    public GameClientProxy(Requestor requestor) {
        this.requestor = requestor;
    }

    @Override
    public int getTurnNumber() {
        int turnNumber = requestor.sendRequestAndAwaitReply(gameId, OperationNames.GAME_GET_TURN_NUMBER, Integer.class);
        return turnNumber;
    }

    @Override
    public Player getPlayerInTurn() {
        Player playerInTurn = requestor.sendRequestAndAwaitReply(gameId, OperationNames.GAME_GET_PLAYER_IN_TURN, Player.class);
        return playerInTurn;
    }

    @Override
    public Hero getHero(Player who) {
        String heroId = requestor.sendRequestAndAwaitReply(gameId, OperationNames.GAME_GET_HERO, String.class, who);
        Hero hero = new HeroClientProxy(requestor, heroId);
        System.out.println(hero.getId());
        return hero;
    }

    @Override
    public Player getWinner() {
        Player winnerPlayer = requestor.sendRequestAndAwaitReply(gameId, OperationNames.GAME_GET_WINNER, Player.class);
        return winnerPlayer;
    }

    @Override
    public int getDeckSize(Player who) {
        int deckSize = requestor.sendRequestAndAwaitReply(gameId, OperationNames.GAME_GET_DECK_SIZE, Integer.class, who);
        return deckSize;
    }

    @Override
    public Card getCardInHand(Player who, int indexInHand) {
        String cardId = requestor.sendRequestAndAwaitReply(gameId, OperationNames.GAME_GET_CARD_IN_HAND, String.class, who, indexInHand);
        Card cardClientProxy = new CardClientProxy(requestor, cardId);
        return cardClientProxy;
    }

    @Override
    public Iterable<? extends Card> getHand(Player who) {
        Type collectionType = new TypeToken<List<String>>(){}.getType();
        List<String> returnedList;
        returnedList = requestor.sendRequestAndAwaitReply(gameId, OperationNames.GAME_GET_HAND, collectionType, who);

        ArrayList<CardClientProxy> cardClientProxies = new ArrayList<>();
        for(String cardId : returnedList){
            cardClientProxies.add(new CardClientProxy(requestor, cardId));
        }

        return cardClientProxies;
    }

    @Override
    public int getHandSize(Player who) {
        int handSize = requestor.sendRequestAndAwaitReply(gameId, OperationNames.GAME_GET_HAND_SIZE, Integer.class, who);
        return handSize;
    }

    @Override
    public Card getCardInField(Player who, int indexInField) {
        String cardId = requestor.sendRequestAndAwaitReply(gameId, OperationNames.GAME_GET_CARD_IN_FIELD, String.class, who, indexInField);
        Card cardClientProxy = new CardClientProxy(requestor, cardId);
        return cardClientProxy;
    }

    @Override
    public Iterable<? extends Card> getField(Player who) {
        Type collectionType = new TypeToken<List<String>>(){}.getType();
        List<String> returnedList;
        returnedList = requestor.sendRequestAndAwaitReply(gameId, OperationNames.GAME_GET_FIELD, collectionType, who);

        ArrayList<Card> cardClientProxies = new ArrayList<>();
        for(String cardId : returnedList){
            cardClientProxies.add(new CardClientProxy(requestor, cardId));
        }

        return cardClientProxies;
    }

    @Override
    public int getFieldSize(Player who) {
        int fieldSize = requestor.sendRequestAndAwaitReply(gameId, OperationNames.GAME_GET_FIELD_SIZE, Integer.class, who);
        return fieldSize;
    }

    @Override
    public void endTurn() {
        requestor.sendRequestAndAwaitReply(gameId, OperationNames.GAME_END_OF_TURN, Void.class);
    }

    @Override
    public Status playCard(Player who, Card card, int atIndex) {
        Status status = requestor.sendRequestAndAwaitReply(gameId, OperationNames.GAME_PLAY_CARD, Status.class, who, card.getId(), atIndex);
        return status;
    }

    @Override
    public Status attackCard(Player playerAttacking, Card attackingCard, Card defendingCard) {
        Status status = requestor.sendRequestAndAwaitReply(gameId, OperationNames.GAME_ATTACK_CARD, Status.class, playerAttacking, attackingCard.getId(), defendingCard.getId());
        return status;
    }

    @Override
    public Status attackHero(Player playerAttacking, Card attackingCard) {
        Status status = requestor.sendRequestAndAwaitReply(gameId, OperationNames.GAME_ATTACK_HERO, Status.class, playerAttacking, attackingCard.getId());
        return status;
    }

    @Override
    public Status usePower(Player who) {
        Status status = requestor.sendRequestAndAwaitReply(gameId, OperationNames.GAME_USE_POWER, Status.class, who);
        return status;
    }

    @Override
    public void addObserver(GameObserver observer) {

    }
}
