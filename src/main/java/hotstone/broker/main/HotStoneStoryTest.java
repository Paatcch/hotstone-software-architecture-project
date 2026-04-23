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

package hotstone.broker.main;

import frds.broker.Requestor;
import frds.broker.ipc.http.UriTunnelClientRequestHandler;
import frds.broker.marshall.json.StandardJSONRequestor;
import hotstone.broker.client.CardClientProxy;
import hotstone.broker.client.GameClientProxy;
import hotstone.broker.client.HeroClientProxy;
import hotstone.broker.common.BrokerConstants;
import hotstone.framework.Card;
import hotstone.framework.Game;
import hotstone.framework.Hero;
import hotstone.framework.Player;

public class HotStoneStoryTest {
  public static void main(String[] args)  {
    // Get the name of the host from the commandline parameters
    String host = args[0];
    // and execute the story test, talking to the server with that name
    new HotStoneStoryTest(host);
  }

  public HotStoneStoryTest(String host) {
    // Create the client side Broker roles
    UriTunnelClientRequestHandler clientRequestHandler
            = new UriTunnelClientRequestHandler(host, BrokerConstants.HOTSTONE_PORT,
            false, BrokerConstants.HOTSTONE_TUNNEL_PATH);
    Requestor requestor = new StandardJSONRequestor(clientRequestHandler);

    Game game = new GameClientProxy(requestor);
    Card card = new CardClientProxy(requestor, "whateverCardID");
    Hero hero = new HeroClientProxy(requestor, "whateverHeroID");
    testSimpleMethods(game, card, hero);
  }

  private void testSimpleMethods(Game game, Card card, Hero hero) {
    System.out.println("=== Testing pass-by-value methods of Game ===");

    System.out.println("\n Game: ");
    System.out.println(" --> Game turnNumber     " + game.getTurnNumber());
    System.out.println(" --> Game winner         " + game.getWinner());
    System.out.println(" --> Game decksize       " + game.getDeckSize(Player.FINDUS));
    System.out.println(" --> Game playerInTurn   " + game.getPlayerInTurn());
    System.out.println(" --> Game handSize       " + game.getHandSize(Player.FINDUS));
    System.out.println(" --> Game fieldSize      " + game.getFieldSize(Player.FINDUS));
    System.out.println(" --> Game endTurn        ");
    game.endTurn();
    System.out.println(" --> Game turnNumber after endTurn   " + game.getTurnNumber());

    System.out.println("\n Card: ");
    System.out.println(" --> Card getName       " + card.getName());
    System.out.println(" --> Card getManaCost   " + card.getManaCost());
    System.out.println(" --> Card getAttack     " + card.getAttack());
    System.out.println(" --> Card getHealth     " + card.getHealth());
    System.out.println(" --> Card isActive      " + card.isActive());
    System.out.println(" --> Card getOwner      " + card.getOwner());
    System.out.println(" --> Card getEffectDescription " + card.getEffectDescription());

    System.out.println("\n Hero: ");
    System.out.println(" --> Hero getMana       " + hero.getMana());
    System.out.println(" --> Hero getHealth     " + hero.getHealth());
    System.out.println(" --> Hero canUsePower   " + hero.canUsePower());
    System.out.println(" --> Hero getType       " + hero.getType());
    System.out.println(" --> Hero getOwner      " + hero.getOwner());
    System.out.println(" --> Hero getEffectDescription " + hero.getEffectDescription());

    System.out.println("\n === End ===");
  }
}
