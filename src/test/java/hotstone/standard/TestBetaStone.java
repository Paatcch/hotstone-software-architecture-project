package hotstone.standard;

import hotstone.factories.BetaStoneFactory;
import hotstone.factories.HotStoneFactory;
import hotstone.framework.Card;
import hotstone.framework.Player;
import org.junit.jupiter.api.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import hotstone.framework.Game;

public class TestBetaStone {
    private Game game;
    private HotStoneFactory betaStoneFactory;

    /** Fixture for BetaStone testing. */
    @BeforeEach
    public void setUp() {
        betaStoneFactory = new BetaStoneFactory();
        game = new StandardHotStoneGame(betaStoneFactory);
    }

    @Test
    public void shouldHave19HealthForPeddersenInTurnNumber9AsHeTriesToDrawCardFromEmptyDeck(){
        //Given game initialized in 'beforeEach' method

        //When it is turnNumber 9
        for(int i = 1; i < 10; i++){
            game.endTurn();//Suffers a two health penalty when drawing card from empty deck in turn nine
        }
        //Then peddersen has 19 health
        assertThat(game.getHero(Player.PEDDERSEN).getHealth(), is(19));
    }

    @Test
    public void shouldHave1ManaForFindusAtFindusFirstTurn(){
        //Given game initialized in 'beforeEach' method

        //When it's findus first turn

        //Then findus has one mana
        assertThat(game.getHero(Player.FINDUS).getMana(), is(1));
    }

    @Test
    public void shouldHave2ManaForFindusAtFindusSecondTurn(){
        //Given game initialized in 'beforeEach' method

        //When it's findus second turn
        game.endTurn();
        game.endTurn();

        //Then findus has two mana
        assertThat(game.getHero(Player.FINDUS).getMana(), is(2));
    }

    @Test
    public void shouldHaveMana7ForFindusAtFindus8thTurn(){
        //Given game initialized in 'beforeEach' method

        //When it is Findus' 8th turn
        for(int i = 1; i < 16; i++){
            game.endTurn();
        }

        //Then he has 7 mana
        assertThat(game.getHero(Player.FINDUS).getMana(), is(7));
    }

    @Test
    public void shouldHave2ManaForPeddersenAtPeddersenSecondTurn(){
        //Given game initialized in 'beforeEach' method

        //When it's Peddersen second turn
        game.endTurn();
        game.endTurn();
        game.endTurn();

        //Then findus has two mana
        assertThat(game.getHero(Player.PEDDERSEN).getMana(), is(2));
    }

    @Test
    public void shouldHaveNoWinnerInTurn9WhenNoPlayerHasAttacked(){
        //Given game initialized in 'beforeEach' method

        //When four rounds have passed
        for(int i = 0; i < 8; i++){
            game.endTurn();
        }

        //Then getWinner() should return null
        assertThat(game.getWinner(), is(nullValue()));
    }

    @Test
    public void shouldHavePeddersenAsWinnerWhenFindusHealthIs0(){
        //Given game initialized in 'beforeEach' method

        //When Findus health becomes 0, because Peddersen attacks
        game.endTurn();
        Card cardUno = game.getCardInHand(Player.PEDDERSEN,3);
        game.playCard(Player.PEDDERSEN, cardUno,0);
        game.endTurn();
        game.endTurn();

        for(int i = 0; i < 9; i++){
            game.attackHero(Player.PEDDERSEN, cardUno);
            game.endTurn();
            game.endTurn();
        }

        //Then Peddersen is the winner
        assertThat(game.getWinner(), is(Player.PEDDERSEN));
    }
    @Test
    public void shouldHavePeddersenAsWinnerWhenFindusHealthIsMinus3(){
        //Given game initialized in 'beforeEach' method

        //When Findus health becomes -3, because Peddersen attacks
        game.endTurn();
        Card cardUno = game.getCardInHand(Player.PEDDERSEN,3);
        game.playCard(Player.PEDDERSEN, cardUno,0);
        game.endTurn();
        game.endTurn();

        for(int i = 0; i < 10; i++){
            game.attackHero(Player.PEDDERSEN, cardUno);
            game.endTurn();
            game.endTurn();
        }

        //Then Peddersen is the winner
        assertThat(game.getWinner(), is(Player.PEDDERSEN));
    }

    @Test
    public void shouldHaveFindusAsWinnerWhenPeddersensHealthIs0(){
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

    @Test
    public void shouldHaveFindusAsWinnerWhenPeddersensHealthIsMinus3(){
        //Given game initialized in 'beforeEach' method

        //When Peddersens health becomes -3, because Findus attacks
        Card cardUno = game.getCardInHand(Player.FINDUS,2);
        game.playCard(Player.FINDUS, cardUno,0);
        game.endTurn();
        game.endTurn();

        for(int i = 0; i < 10; i++){
            game.attackHero(Player.FINDUS, cardUno);
            game.endTurn();
            game.endTurn();
        }

        //Then Findus is the winner
        assertThat(game.getWinner(), is(Player.FINDUS));
    }
}
