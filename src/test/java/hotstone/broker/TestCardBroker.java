package hotstone.broker;

import frds.broker.ClientRequestHandler;
import frds.broker.Invoker;
import frds.broker.Requestor;
import frds.broker.marshall.json.StandardJSONRequestor;
import hotstone.broker.client.CardClientProxy;
import hotstone.broker.client.GameClientProxy;
import hotstone.broker.doubles.LocalMethodClientRequestHandler;
import hotstone.broker.server.HotStoneGameInvoker;
import hotstone.factories.AlphaStoneFactory;
import hotstone.framework.Card;
import hotstone.framework.Game;
import hotstone.framework.Player;
import hotstone.standard.StandardHotStoneGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestCardBroker {
    // The client side's card client proxy
    private Card cardClientProxy;
    private Game gameClientProxy;

    @BeforeEach
    public void setup() {
        Game servant = new StandardHotStoneGame(new AlphaStoneFactory());
        Invoker invoker = new HotStoneGameInvoker(servant);
        ClientRequestHandler crh =
                new LocalMethodClientRequestHandler(invoker);
        Requestor requestor = new StandardJSONRequestor(crh);
        gameClientProxy = new GameClientProxy(requestor);
        Card unoCard = gameClientProxy.getCardInHand(Player.FINDUS, 2);
        cardClientProxy = new CardClientProxy(requestor, unoCard.getId());
    }

    @Test
    public void shouldReturnSomeNameForGetName(){
        // Given an uno card initialized in beforeEach method

        // When I ask for the name on the client side
        String name = cardClientProxy.getName();

        //Then the broker chain will return the reply
        assertThat(name, is("Uno"));
    }

    @Test
    public void shouldReturn1ForGetManaCost(){
        // Given an uno card initialized in beforeEach method

        // When I ask for the mana cost on the client side
        int manaCost = cardClientProxy.getManaCost();

        //Then the broker chain will return the reply
        assertThat(manaCost, is(1));
    }

    @Test
    public void shouldReturn1ForGetAttack(){
        // Given an uno card initialized in beforeEach method

        // When I ask for the attack on the client side
        int attack = cardClientProxy.getAttack();

        //Then the broker chain will return the reply
        assertThat(attack, is(1));
    }

    @Test
    public void shouldReturn1ForGetHealth(){
        // Given an uno card initialized in beforeEach method

        // When I ask for the health on the client side
        int health = cardClientProxy.getHealth();

        //Then the broker chain will return the reply
        assertThat(health, is(1));
    }

    @Test
    public void shouldReturnFalseForIsActive(){
        // Given an uno card initialized in beforeEach method

        // When I ask if the card is active on the client side
        boolean isActive = cardClientProxy.isActive();

        //Then the broker chain will return the reply
        assertThat(isActive, is(false));
    }

    @Test
    public void shouldReturnFINDUSForGetOwner(){
        // Given an uno card initialized in beforeEach method

        // When I ask for the owner on the client side
        Player owner = cardClientProxy.getOwner();

        //Then the broker chain will return the stub's "some name" reply
        assertThat(owner, is(Player.FINDUS));
    }

    @Test
    public void shouldReturnNothingForGetEffectDescription(){
        // Given an uno card initialized in beforeEach method

        // When I ask for the effect description on the client side
        String effectDescription = cardClientProxy.getEffectDescription();

        //Then the broker chain will return the reply
        assertThat(effectDescription, is(""));
    }

}
