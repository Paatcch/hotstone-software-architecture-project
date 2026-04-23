package hotstone.broker;

import frds.broker.ClientRequestHandler;
import frds.broker.Invoker;
import frds.broker.Requestor;
import frds.broker.marshall.json.StandardJSONRequestor;
import hotstone.broker.client.GameClientProxy;
import hotstone.broker.client.HeroClientProxy;
import hotstone.broker.doubles.LocalMethodClientRequestHandler;
import hotstone.broker.doubles.StubGameForBroker;
import hotstone.broker.server.HotStoneGameInvoker;
import hotstone.factories.AlphaStoneFactory;
import hotstone.framework.Game;
import hotstone.framework.Hero;
import hotstone.framework.Player;
import hotstone.standard.StandardHotStoneGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestHeroBroker {
    // The client side's hero client proxy
    private Hero heroClientProxy;
    private Game gameClientProxy;

    @BeforeEach
    public void setup() {
        Game servant = new StandardHotStoneGame(new AlphaStoneFactory());
        Invoker invoker = new HotStoneGameInvoker(servant);
        ClientRequestHandler crh =
                new LocalMethodClientRequestHandler(invoker);
        Requestor requestor = new StandardJSONRequestor(crh);

        gameClientProxy = new GameClientProxy(requestor);
        heroClientProxy = gameClientProxy.getHero(Player.FINDUS);
    }

    @Test
    public void shouldReturn3ForGetMana(){
        // Given Findus' Hero which is initialized by beforeEach method

        // When I ask for the mana on the client side
        int mana = heroClientProxy.getMana();

        //Then the broker chain will return the reply
        assertThat(mana, is(3));
    }

    @Test
    public void shouldReturn1ForGetHealth(){
        // Given Findus' Hero which is initialized by beforeEach method

        // When I ask for the mana on the client side
        int health = heroClientProxy.getHealth();

        //Then the broker chain will return reply
        assertThat(health, is(21));
    }

    @Test
    public void shouldReturnTrueForCanUsePower(){
        // Given Findus' Hero which is initialized by beforeEach method

        // When I ask if the player can use power on the client side
        boolean canUsePower = heroClientProxy.canUsePower();

        //Then the broker chain will return the reply
        assertThat(canUsePower, is(true));
    }

    @Test
    public void shouldReturnSomeTypeForGetType(){
        // Given Findus' Hero which is initialized by beforeEach method

        // When I ask for the type on the client side
        String type = heroClientProxy.getType();

        //Then the broker chain will return the reply
        assertThat(type, is("Baby"));
    }

    @Test
    public void shouldReturnFindusForGetOwner(){
        // Given Findus' Hero which is initialized by beforeEach method

        // When I ask for the owner on the client side
        Player owner = heroClientProxy.getOwner();

        //Then the broker chain will return the reply
        assertThat(owner, is(Player.FINDUS));
    }

    @Test
    public void shouldReturnSomeEffectForGetEffectDescription(){
        // Given Findus' Hero which is initialized by beforeEach method

        // When I ask for the owner on the client side
        String effectDesription = heroClientProxy.getEffectDescription();

        //Then the broker chain will return the reply
        assertThat(effectDesription, is("Just Cute"));
    }
}