package hotstone.standard;

import hotstone.factories.HotStoneFactory;
import hotstone.factories.ZetaStoneFactory;
import hotstone.framework.Card;
import hotstone.framework.Game;
import hotstone.framework.Player;
import hotstone.variants.winnerStrategy.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestZetaStone {
    private Game game;
    HotStoneFactory zetaStoneFactory;

    /** Fixture for ZetaStone testing. */
    @BeforeEach
    public void setUp() {
        zetaStoneFactory = new ZetaStoneFactory();
        game = new StandardHotStoneGame(zetaStoneFactory);
    }

    @Test
    public void shouldHavePeddersenAsWinnerAtBeginningOfRound4WhenThereAreNoMinionsOnField(){
        //Given a game initialized by beforeEach method

        //When round 4 begins and there are no minions on the field
        for(int i = 0; i < 6; i++){
            game.endTurn();
        }

        //Then Peddersen is winner
        assertThat(game.getWinner(), is(Player.PEDDERSEN));
    }

    @Test
    public void shouldHaveFindusAsWinnerWhenPeddersensHealthIs0InTurn21(){
        //Given game initialized in 'beforeEach' method

        //When Peddersens health becomes 0, because Findus attacks
        Card cardUno = game.getCardInHand(Player.FINDUS,2);
        game.playCard(Player.FINDUS, cardUno,0);
        game.endTurn();
        game.endTurn();

        for(int i = 0; i < 9; i++){
            game.attackHero(Player.FINDUS, cardUno);
            game.endTurn();
            game.endTurn();
        }

        //Then Findus is the winner
        assertThat(game.getWinner(), is(Player.FINDUS));
    }

}
