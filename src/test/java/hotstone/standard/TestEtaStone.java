package hotstone.standard;
import hotstone.factories.EtaStoneFactory;
import hotstone.factories.HotStoneFactory;
import hotstone.framework.Card;
import hotstone.framework.Player;
import hotstone.variants.deckStrategy.DishDeckStrategy;
import org.junit.jupiter.api.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

import hotstone.framework.Game;

import java.util.ArrayList;

public class TestEtaStone {
    private Game game;
    private HotStoneFactory etaStoneFactory;

    /** Fixture for EtaStone testing.  */

    @BeforeEach
    public void setUp() {
        etaStoneFactory = new EtaStoneFactory();
        game = new StandardHotStoneGame(etaStoneFactory);
    }

    @Test
    public void PeddersenShouldHaveHealth20WhenFindusPlaysBrownRiceInTurn1(){
        //Given a game initialized by the before each method

        //When Findus plays the brown rice

        //Get the brown rice card
        Card brownRiceCard = null;

        //Checks the three cards in his hand
        Card card = game.getCardInHand(Player.FINDUS, 0);
        if(isBrownRiceCard(card)) { brownRiceCard = card; }

        card = game.getCardInHand(Player.FINDUS, 1);
        if(isBrownRiceCard(card)) { brownRiceCard = card; }

        card = game.getCardInHand(Player.FINDUS, 2);
        if(isBrownRiceCard(card)) { brownRiceCard = card; }

        //Checks the rest of the cards
        for (int i = 0; i < 30; i++){
            game.endTurn();
            if(game.getPlayerInTurn().equals(Player.FINDUS)){
                card = game.getCardInHand(Player.FINDUS, 0);
                if(isBrownRiceCard(card)) { brownRiceCard = card; }
            }
        }

        game.playCard(Player.FINDUS, brownRiceCard, 0);

        //Then Peddersen has 20 health
        assertThat(game.getHero(Player.PEDDERSEN).getHealth(), is(20));
    }

    public Boolean isBrownRiceCard(Card card){
        return card.getName().equals(GameConstants.BROWN_RICE_CARD);
    }
}
