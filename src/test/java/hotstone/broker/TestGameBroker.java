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
package hotstone.broker;

import frds.broker.ClientRequestHandler;
import frds.broker.Invoker;
import frds.broker.Requestor;
import frds.broker.marshall.json.StandardJSONRequestor;
import hotstone.broker.client.CardClientProxy;
import hotstone.broker.client.GameClientProxy;
import hotstone.broker.doubles.LocalMethodClientRequestHandler;
import hotstone.broker.doubles.StubGameForBroker;
import hotstone.broker.server.HotStoneGameInvoker;
import hotstone.factories.AlphaStoneFactory;
import hotstone.framework.*;
import hotstone.standard.StandardCard;
import hotstone.standard.StandardHero;
import hotstone.standard.StandardHotStoneGame;
import io.javalin.websocket.WsHandlerEntry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Skeleton test case for a 'depth-first' test-driven
 * development process to develop the Broker implementation
 * of client proxies and invokers, for all 'primitive' methods
 * in Game, that is, methods that do NOT take objects as
 * parameters, only primitive types and Strings.
 */
public class TestGameBroker {
    // The client side's game client proxy
    private Game gameClientProxy;

    @BeforeEach
    public void setup() {

        // === We start at the server side of the Broker pattern:
        // define the servant, next the invoker

        // Given a Servant game, here a alphaStone as the most simple version
        Game servant = new StandardHotStoneGame(new AlphaStoneFactory());
        // Which is injected into the dedicated Invoker which you must
        // develop
        Invoker invoker = new HotStoneGameInvoker(servant);

        // === Next define the client side of the pattern:
        // the client request handler, the requestor, and the client proxy

        // Instead of a network-based client- and server request handler
        // we make a fake object CRH that talks directly with the injected
        // invoker
        ClientRequestHandler crh =
                new LocalMethodClientRequestHandler(invoker);

        // Which is injected into the standard JSON requestor of the
        // FRDS.Broker library
        Requestor requestor = new StandardJSONRequestor(crh);

        // Which is finally injected into the GameClientProxy that
        // you must develop...
        gameClientProxy = new GameClientProxy(requestor);
    }

    // TODO: TDD the 'clientproxy' and 'invoker' implementation
    // to support remote method call of getTurnNumber()
    @Test
    public void shouldHaveTurnNumber1() {
        // Given a stub game which is hard coded to
        // return 1 as turn number

        // When I ask for that turn number on the client side
        int turnNumber = gameClientProxy.getTurnNumber();

        // Then the broker chain (clientProxy -> requestor ->
        // client request handler -> invoker -> servant) will
        // return 1
        assertThat(turnNumber, is(1));
    }

    @Test
    public void shouldReturn900ForGetDeckSize(){
        //Given a game initialized by @beforeEach method

        //When i ask for the deckSize for Peddersen on the client side
        int deckSize = gameClientProxy.getDeckSize(Player.PEDDERSEN);

        //Then the broker chain returns 4
        assertThat(deckSize, is(4));
    }

    @Test
    public void shouldReturnFindusForGetPlayerInTurn(){
        //Given a stub game initialized by @beforeEach method, which is hard coded to return findus as player in turn

        //When i ask for the player in turn
        Player player = gameClientProxy.getPlayerInTurn();

        //Then the broker chain returns the stub's peddersen reply
        assertThat(player, is(Player.FINDUS));
    }

    @Test
    public void shouldReturnFindusForGetWinner(){
        //Given a stub game initialized by @beforeEach method

        //When i ask for the player in turn after round 4
        for(int i = 0; i < 8; i++){
            gameClientProxy.endTurn();
        }
        Player player = gameClientProxy.getWinner();

        //Then the broker chain returns FINDUS
        assertThat(player, is(Player.FINDUS));
    }

    @Test
    public void shouldReturn3ForGetHandSize(){
        //Given a game initialized by @beforeEach method

        //When i ask for the player in turn
        int handSize = gameClientProxy.getHandSize(Player.PEDDERSEN);

        //Then the broker chain returns Aplhastone peddersens reply
        assertThat(handSize, is(3));
    }

    @Test
    public void shouldReturn0ForGetFieldSize(){
        //Given a game initialized by @beforeEach method

        //When I get fieldSize
        int fieldSize = gameClientProxy.getFieldSize(Player.PEDDERSEN);

        //Then the broker chain returns 0
        assertThat(fieldSize, is(0));
    }

    @Test
    public void shouldAdd1toTurnNumberForEndTurn(){
        //Given a game initialized by @beforeEach method

        //When i end turn
        gameClientProxy.endTurn();

        //Then the turn has ended and the turnNumber is 1+1
        assertThat(gameClientProxy.getTurnNumber(), is(2));
    }

    @Test
    public void shouldGetCardInHand(){
        //Given a game initialized by @beforeEach method

        //When I get the dos card from Findus' hand
        Card dos = gameClientProxy.getCardInHand(Player.FINDUS, 1);

        //Then it is a valid card
        assertThat(dos, is(notNullValue()));

        //Then it is the card dos
        assertThat(dos.getName(),is("Dos"));
        assertThat(dos.getAttack(),is(2));
    }


    @Test
    public void shouldGetCardInField(){
        //Given a game initialized by @beforeEach method

        //When Findus play the Dos card and try to get it from field
        Card dos = gameClientProxy.getCardInHand(Player.FINDUS, 1);
        System.out.println("name: " + dos.getName());
        gameClientProxy.playCard(Player.FINDUS, dos, 0);
        Card cardFromField = gameClientProxy.getCardInField(Player.FINDUS, 0);

        //Then it is a valid card
        assertThat(cardFromField, is(notNullValue()));

        //Then it is the card dos
        assertThat(cardFromField.getName(),is("Dos"));
        assertThat(cardFromField.getAttack(),is(2));
    }

    @Test
    public void shouldGetField(){
        //Given a game initialized by @beforeEach method

        //When Findus has played Uno
        Card uno = gameClientProxy.getCardInHand(Player.FINDUS, 2);
        gameClientProxy.playCard(Player.FINDUS, uno, 0);

        //Then the getField() has length 1
        Iterable<? extends Card> fieldList = gameClientProxy.getField(Player.FINDUS);

        int sizeOfList = 0;
        for(Card card : fieldList){
            sizeOfList++;
        }

        assertThat(sizeOfList, is(1));
    }

    @Test
    public void shouldGetHand(){
        //Given a game initialized by @beforeEach method

        //When the game has started

        //Then the getHand() has length 3 for Findus
        Iterable<? extends Card> handList = gameClientProxy.getHand(Player.FINDUS);

        int sizeOfList = 0;
        for(Card card : handList){
            sizeOfList++;
        }

        assertThat(sizeOfList, is(3));
    }

    @Test
    public void shouldGetHero(){
        //Given a game initialized by @beforeEach method

        //When the game has started and we call getHero for Findus
        Hero hero = gameClientProxy.getHero(Player.FINDUS);
        System.out.println(hero.getId());
        //Then the returned hero is of type Baby and has owner Findus
        assertThat(hero.getType(), is("Baby"));
        assertThat(hero.getOwner(), is(Player.FINDUS));
    }

    @Test
    public void shouldUsePower(){
        //Given a game initialized by @beforeEach method

        //When findus tries to use power
        Status status = gameClientProxy.usePower(Player.FINDUS);

        //Then STATUS.OK is returned
        assertThat(gameClientProxy.getHero(Player.FINDUS).getMana(), is(1));
        assertThat(status, is(Status.OK));
    }

    @Test
    public void shouldAttackCard(){
        //Given a game initialized by @beforeEach method

        //When Findus Dos minion tries to attack Peddersens Uno Minion
        Card cardDos = gameClientProxy.getCardInHand(Player.FINDUS, 1);
        gameClientProxy.playCard(Player.FINDUS, cardDos, 0);
        gameClientProxy.endTurn();

        Card cardUno = gameClientProxy.getCardInHand(Player.PEDDERSEN, 3);
        System.out.println("______" + cardUno.getName() + "something: " + cardUno);
        System.out.println("______" + cardDos.getName() + "something: " + cardDos);
        gameClientProxy.playCard(Player.PEDDERSEN, cardUno, 0);
        gameClientProxy.endTurn();

        gameClientProxy.attackCard(Player.FINDUS, cardDos, cardUno);

        //Then Peddersens Uno minion is dead
        assertThat(gameClientProxy.getFieldSize(Player.PEDDERSEN),is(0));
    }

    @Test
    public void shouldAttackHero(){
        //Given a game initialized by @beforeEach method

        //When Findus tries to attack peddersens hero (using uno)
        Card cardUno = gameClientProxy.getCardInHand(Player.FINDUS, 2);
        gameClientProxy.playCard(Player.FINDUS, cardUno, 0);
        gameClientProxy.endTurn();
        gameClientProxy.endTurn();
        Status status = gameClientProxy.attackHero(Player.FINDUS, cardUno);

        //Then Status.OK is returned
        assertThat(status, is(Status.OK));
    }


}
