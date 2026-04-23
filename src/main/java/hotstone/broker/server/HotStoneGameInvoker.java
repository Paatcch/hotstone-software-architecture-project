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

package hotstone.broker.server;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import frds.broker.Invoker;
import frds.broker.ReplyObject;
import frds.broker.RequestObject;
import hotstone.broker.common.OperationNames;
import hotstone.broker.doubles.StubCardForBroker;
import hotstone.broker.doubles.StubHeroForBroker;
import hotstone.framework.*;
import hotstone.standard.StandardCard;
import hotstone.variants.winnerStrategy.FindusWinnerStrategy;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.config.PropertyGetter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * TODO: Template code for solving the Broker exercises
 */
public class HotStoneGameInvoker implements Invoker {
    private final Game game;
    private final Gson gson;
    private HashMap<String, Card> nameServiceCard;
    private HashMap<String, Hero> nameServiceHero;

    public HotStoneGameInvoker(Game servant) {
        gson = new Gson();
        game = servant;
        nameServiceCard = new HashMap<>();
        nameServiceHero = new HashMap<>();
    }

    @Override
    public String handleRequest(String request) {
        RequestObject requestObject = gson.fromJson(request, RequestObject.class);
        JsonArray array = JsonParser.parseString(requestObject.getPayload()).getAsJsonArray();

        ReplyObject reply;

        try {
            reply = switch (requestObject.getOperationName()) {
                case OperationNames.GAME_GET_TURN_NUMBER -> {
                    int turnNumber = game.getTurnNumber();
                    yield new ReplyObject(HttpServletResponse.SC_CREATED, gson.toJson(turnNumber));
                }
                case OperationNames.GAME_GET_DECK_SIZE -> {
                    Player who = gson.fromJson(array.get(0), Player.class);//Fetch the parameter

                    int deckSize = game.getDeckSize(who);
                    yield new ReplyObject(HttpServletResponse.SC_CREATED, gson.toJson(deckSize));
                }
                case OperationNames.GAME_GET_PLAYER_IN_TURN -> {
                    Player playerInTurn = game.getPlayerInTurn();
                    yield new ReplyObject(HttpServletResponse.SC_CREATED, gson.toJson(playerInTurn));
                }
                case OperationNames.GAME_GET_WINNER -> {
                    Player winnerPlayer = game.getWinner();
                    yield new ReplyObject(HttpServletResponse.SC_CREATED, gson.toJson(winnerPlayer));
                }
                case OperationNames.GAME_GET_HAND_SIZE -> {
                    Player who = gson.fromJson(array.get(0), Player.class);

                    int handSize = game.getHandSize(who);
                    yield new ReplyObject(HttpServletResponse.SC_CREATED, gson.toJson(handSize));
                }
                case OperationNames.GAME_GET_FIELD_SIZE -> {
                    Player who = gson.fromJson(array.get(0), Player.class);
                    int fieldSize = game.getFieldSize(who);
                    yield new ReplyObject(HttpServletResponse.SC_CREATED, gson.toJson(fieldSize));
                }
                case OperationNames.GAME_END_OF_TURN -> {
                    game.endTurn();
                    yield new ReplyObject(HttpServletResponse.SC_OK, "");
                }
                case OperationNames.GAME_USE_POWER -> {
                    Player who = gson.fromJson(array.get(0), Player.class);
                    Status status = game.usePower(who);
                    yield new ReplyObject(HttpServletResponse.SC_CREATED, gson.toJson(status));
                }
                case OperationNames.GAME_ATTACK_HERO -> {
                    Player playerAttacking = gson.fromJson(array.get(0), Player.class);
                    String cardId = gson.fromJson(array.get(1), String.class);
                    Card card = nameServiceCard.get(cardId);

                    Status status = game.attackHero(playerAttacking, card);

                    yield new ReplyObject(HttpServletResponse.SC_CREATED, gson.toJson(status));

                }

                case OperationNames.GAME_ATTACK_CARD -> {
                    Player who = gson.fromJson(array.get(0), Player.class);
                    String attackingCardId = gson.fromJson(array.get(1), String.class);
                    Card attackingCardFromNameService = nameServiceCard.get(attackingCardId);
                    String defendingCardId = gson.fromJson(array.get(2), String.class);
                    Card defendingCardFromNameService = nameServiceCard.get(defendingCardId);

                    Status status = game.attackCard(who, attackingCardFromNameService, defendingCardFromNameService);
                    yield new ReplyObject(HttpServletResponse.SC_CREATED, gson.toJson(status));
                }



                case OperationNames.GAME_GET_CARD_IN_HAND -> {
                    //Fetch the parameters
                    Player who = gson.fromJson(array.get(0), Player.class);
                    int index = gson.fromJson(array.get(1), Integer.class);

                    Card card = game.getCardInHand(who, index);
                    String cardId = card.getId();

                    nameServiceCard.putIfAbsent(cardId, card);

                    yield new ReplyObject(HttpServletResponse.SC_CREATED, gson.toJson(cardId));
                }
                case OperationNames.GAME_GET_CARD_IN_FIELD -> {
                    //Fetch the parameters
                    Player who = gson.fromJson(array.get(0), Player.class);
                    int index = gson.fromJson(array.get(1), Integer.class);

                    Card card = game.getCardInField(who, index);
                    String cardId = card.getId();
                    nameServiceCard.putIfAbsent(cardId, card);

                    yield new ReplyObject(HttpServletResponse.SC_CREATED, gson.toJson(cardId));
                }
                case OperationNames.GAME_PLAY_CARD -> {
                    //Fetch the parameters
                    Player who = gson.fromJson(array.get(0), Player.class);
                    String cardId = gson.fromJson(array.get(1), String.class);

                    Card card = nameServiceCard.get(cardId);
                    int index = gson.fromJson(array.get(2), Integer.class);

                    Status status = game.playCard(who, card, index);

                    yield new ReplyObject(HttpServletResponse.SC_CREATED, gson.toJson(status));
                }
                case OperationNames.GAME_GET_FIELD -> {
                    Player who = gson.fromJson(array.get(0), Player.class);

                    Iterable<? extends Card> cardsInField = game.getField(who);
                    ArrayList<String> cardIds = new ArrayList<>();
                    for (Card card : cardsInField){
                        cardIds.add(card.getId());
                        nameServiceCard.putIfAbsent(card.getId(), card);
                    }

                    yield new ReplyObject(HttpServletResponse.SC_CREATED, gson.toJson(cardIds));
                }
                case OperationNames.GAME_GET_HAND-> {
                    Player who = gson.fromJson(array.get(0), Player.class);

                    Iterable<? extends Card> cardsInHand = game.getHand(who);
                    ArrayList<String> cardIds = new ArrayList<>();
                    for (Card card : cardsInHand){
                        cardIds.add(card.getId());
                        nameServiceCard.putIfAbsent(card.getId(), card);
                    }

                    yield new ReplyObject(HttpServletResponse.SC_CREATED, gson.toJson(cardIds));
                }
                case OperationNames.GAME_GET_HERO -> {
                    Player who = gson.fromJson(array.get(0), Player.class);
                    Hero hero = game.getHero(who);
                    nameServiceHero.putIfAbsent(hero.getId(), hero);

                    yield new ReplyObject(HttpServletResponse.SC_CREATED, gson.toJson(hero.getId()));
                }
                //Hero cases
                case OperationNames.HERO_GET_MANA -> {
                    String heroId = requestObject.getObjectId();
                    Hero hero = nameServiceHero.get(heroId);

                    int mana = hero.getMana();
                    yield new ReplyObject(HttpServletResponse.SC_CREATED, gson.toJson(mana));
                }
                case OperationNames.HERO_GET_HEALTH -> {
                    String heroId = requestObject.getObjectId();
                    Hero hero = nameServiceHero.get(heroId);

                    int health = hero.getHealth();
                    yield new ReplyObject(HttpServletResponse.SC_CREATED, gson.toJson(health));
                }
                case OperationNames.HERO_CAN_USE_POWER -> {
                    String heroId = requestObject.getObjectId();
                    Hero hero = nameServiceHero.get(heroId);

                    boolean canUsePower = hero.canUsePower();
                    yield new ReplyObject(HttpServletResponse.SC_CREATED, gson.toJson(canUsePower));
                }
                case OperationNames.HERO_GET_TYPE -> {
                    String heroId = requestObject.getObjectId();
                    Hero hero = nameServiceHero.get(heroId);

                    String type = hero.getType();
                    yield new ReplyObject(HttpServletResponse.SC_CREATED, gson.toJson(type));
                }
                case OperationNames.HERO_GET_OWNER -> {
                    String heroId = requestObject.getObjectId();
                    Hero hero = nameServiceHero.get(heroId);

                    Player owner = hero.getOwner();
                    yield new ReplyObject(HttpServletResponse.SC_CREATED, gson.toJson(owner));
                }
                case OperationNames.HERO_GET_EFFECT_DESCRIPTION -> {
                    String heroId = requestObject.getObjectId();
                    Hero hero = nameServiceHero.get(heroId);

                    String effectDescription = hero.getEffectDescription();
                    yield new ReplyObject(HttpServletResponse.SC_CREATED, gson.toJson(effectDescription));
                }

                //Card cases
                case OperationNames.CARD_GET_NAME -> {
                    String cardId = requestObject.getObjectId();
                    String name = nameServiceCard.get(cardId).getName();

                    yield new ReplyObject(HttpServletResponse.SC_CREATED, gson.toJson(name));
                }
                case OperationNames.CARD_GET_MANA_COST -> {
                    String cardId = requestObject.getObjectId();
                    Card card = nameServiceCard.get(cardId);

                    int manaCost = card.getManaCost();
                    yield new ReplyObject(HttpServletResponse.SC_CREATED, gson.toJson(manaCost));
                }
                case OperationNames.CARD_GET_ATTACK -> {
                    String cardId = requestObject.getObjectId();
                    Card card = nameServiceCard.get(cardId);

                    int attack = card.getAttack();
                    yield new ReplyObject(HttpServletResponse.SC_CREATED, gson.toJson(attack));
                }
                case OperationNames.CARD_GET_HEALTH -> {
                    String cardId = requestObject.getObjectId();
                    Card card = nameServiceCard.get(cardId);

                    int health = card.getHealth();
                    yield new ReplyObject(HttpServletResponse.SC_CREATED, gson.toJson(health));
                }
                case OperationNames.CARD_IS_ACTIVE -> {
                    String cardId = requestObject.getObjectId();
                    Card card = nameServiceCard.get(cardId);

                    boolean isActive = card.isActive();
                    yield new ReplyObject(HttpServletResponse.SC_CREATED, gson.toJson(isActive));
                }
                case OperationNames.CARD_GET_OWNER -> {
                    String cardId = requestObject.getObjectId();
                    Card card = nameServiceCard.get(cardId);

                    Player owner = card.getOwner();
                    yield new ReplyObject(HttpServletResponse.SC_CREATED, gson.toJson(owner));
                }
                case OperationNames.CARD_GET_EFFECT_DESCRIPTION -> {
                    String cardId = requestObject.getObjectId();
                    Card card = nameServiceCard.get(cardId);

                    String effectDescription = card.getEffectDescription();
                    yield new ReplyObject(HttpServletResponse.SC_CREATED, gson.toJson(effectDescription));
                }

                default -> {
                    // Unknown operation
                    yield new ReplyObject(HttpServletResponse.
                            SC_NOT_IMPLEMENTED,
                            "Server received unknown operation name: '"
                                    + requestObject.getOperationName() + "'.");
                }
            };
        } catch (Exception e) {
            reply = new ReplyObject(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
        // And marshall the reply
        return gson.toJson(reply);
    }

}
