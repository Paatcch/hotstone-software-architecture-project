package hotstone.standard;

import hotstone.factories.GammaStoneFactory;
import hotstone.factories.HotStoneFactory;
import hotstone.framework.Card;
import hotstone.framework.Player;
import org.junit.jupiter.api.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import hotstone.framework.Game;

public class TestGammaStone {
    private Game game;
    private HotStoneFactory gammaStoneFactory;

    /** Fixture for GammaStone testing. */
    @BeforeEach
    public void setUp() {
        gammaStoneFactory = new GammaStoneFactory();
        game = new StandardHotStoneGame(gammaStoneFactory);
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
    public void shouldHaveFindusAsWinnerAtTheStartOfRound4WhenPeddersenHasNoMinionsAndFindusHasUno(){
        //Given a game initialized by beforeEach method

        //When round 4 begins, Peddersen has no minions and Findus has Uno in field
        Card unoCard = game.getCardInHand(Player.FINDUS,2);
        game.playCard(Player.FINDUS,unoCard,0);
        for(int i = 0; i < 6; i++){
            game.endTurn();
        }

        //Then Findus is winner
        assertThat(game.getWinner(), is(Player.FINDUS));
    }

    @Test
    public void shouldHaveNoWinnerAtBeginningOfRound4WhenBothPlayersHaveUnoInTheirFields(){
        //Given a game initialized by beforeEach method

        //When round 4 begins and both players have card uno in the field
        Card unoCardF = game.getCardInHand(Player.FINDUS,2);
        game.playCard(Player.FINDUS,unoCardF,0);
        game.endTurn();

        Card unoCardP = game.getCardInHand(Player.PEDDERSEN,3);
        game.playCard(Player.PEDDERSEN,unoCardP,0);

        for(int i = 0; i < 5; i++){
            game.endTurn();
        }

        //Then winner is null
        assertThat(game.getWinner(), is(nullValue()));
    }

    @Test
    public void shouldHavePeddersenAsWinnerAtTheStartOfRound4WhenFindusHasNoMinionsAndPeddersenHasUno() {
        //Given a game initialized by beforeEach method

        //When round 4 begins, Findus has no minions and Peddersen has Uno in field
        game.endTurn();
        Card unoCard = game.getCardInHand(Player.PEDDERSEN,3);
        game.playCard(Player.PEDDERSEN,unoCard,0);
        for(int i = 0; i < 5; i++){
            game.endTurn();
        }

        //Then Peddersen is winner
        assertThat(game.getWinner(), is(Player.PEDDERSEN));
    }

    @Test
    public void shouldHaveNoWinnerInTurn6(){
        //Given a game initialized by beforeEach method

        //When it is turn 6
        for(int i = 0; i < 5; i++){
            game.endTurn();
        }

        //Then there is no winner
        assertThat(game.getWinner(), is(nullValue()));
    }

    @Test
    public void shouldHaveHeroTypeThaiChefWithEffectDescriptionDeal2DamageToOpponentHeroForFindus(){
        //Given a game initialized by beforeEach method

        //When game starts

        //Then Findus hero type is ThaiChef with effectDescription "Deal 2 damage to opponent hero"
        assertThat(game.getHero(Player.FINDUS).getType(), is(GameConstants.THAI_CHEF_HERO_TYPE));
        assertThat(game.getHero(Player.FINDUS).getEffectDescription(), is("Deal 2 damage to opponent hero"));
    }

    @Test
    public void shouldHaveHeroTypeDanishChefWithEffectDescriptionSummonSovsCardForPeddersen(){
        //Given a game initialized by beforeEach method

        //When game starts

        //Then Peddersen hero type is DanishChef with effectDescription "Summon Sovs card"
        assertThat(game.getHero(Player.PEDDERSEN).getType(), is(GameConstants.DANISH_CHEF_HERO_TYPE));
        assertThat(game.getHero(Player.PEDDERSEN).getEffectDescription(), is("Summon Sovs card"));
    }

    @Test
    public void shouldHaveHealth19ForPeddersenAfterFindusUsesHeroPowerInTurn1(){
        //Given a game initialized by beforeEach method

        //When findus uses hero power
        game.usePower(Player.FINDUS);

        //Then Peddersen has 19 health
        assertThat(game.getHero(Player.PEDDERSEN).getHealth(), is(19));
    }

    @Test
    public void shouldHaveSovsMinionInPeddersensFieldAfterUsingHeroPower(){
        //Given a game initialized by beforeEach method

        //When Peddersen uses hero power
        game.endTurn();
        game.usePower(Player.PEDDERSEN);

        //Then Peddersen has 'Sovs' minion in field with attack = 1 and health = 1
        assertThat(game.getFieldSize(Player.PEDDERSEN), is(1));

        Card cardSovs = game.getCardInField(Player.PEDDERSEN, 0);
        assertThat(cardSovs.getName(), is("Sovs"));
        assertThat(cardSovs.getAttack(), is(1));
        assertThat(cardSovs.getHealth(), is(1));
    }

}
