package hotstone.standard;
//unit testing with test spy

import TestStub.GameObserverTestSpy;
import hotstone.factories.AlphaStoneFactory;
import hotstone.factories.GammaStoneFactory;
import hotstone.framework.Card;
import hotstone.framework.Player;
import hotstone.observer.GameObserver;
import org.junit.jupiter.api.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestGameObserver {
    GameObserverTestSpy gameObserverTestSpy;
    AlphaStoneFactory alphaStoneFactory;
    StandardHotStoneGame game;

    @BeforeEach
    public void setUp() {
        alphaStoneFactory = new AlphaStoneFactory();
        game = new StandardHotStoneGame(alphaStoneFactory);
        gameObserverTestSpy = new GameObserverTestSpy();
        game.addObserver(gameObserverTestSpy);
    }

    @Test
    public void shouldNotifyCorrectlyWhenDanishChefUsesPower(){
        //Given a gammeStone game with a Danish Chef Hero for Peddersen
        GammaStoneFactory gammaStoneFactory = new GammaStoneFactory();
        game = new StandardHotStoneGame(gammaStoneFactory);
        gameObserverTestSpy = new GameObserverTestSpy();
        game.addObserver(gameObserverTestSpy);

        //When Peddersen Uses his hero power
        game.endTurn();
        game.usePower(Player.PEDDERSEN);

        //Then the last method called in GameObserverTestSpy is "onPlayCard(PEDDERSEN, Sovs, 0)"
        assertThat(gameObserverTestSpy.getAllMethodCalled().get(2), is("onPlayCard(PEDDERSEN, Sovs, 0)"));
        assertThat(gameObserverTestSpy.getAllMethodCalled().get(1), is("onHeroUpdate(PEDDERSEN)"));
        assertThat(gameObserverTestSpy.getLastMethodCalled(), is("onUsePower(PEDDERSEN)"));
    }

    @Test
    public void shouldReturnRightParametersOnPlayCard(){
        //Given a game and gameObserver initialized in beforeEach()

        //When playCard is called with parameters (FINDUS, unoCard, 0)
        Card unoCard = game.getCardInHand(Player.FINDUS, 2);
        game.playCard(Player.FINDUS, unoCard, 0);

        //Then the last method called in GameObserverTestSpy is ""onPlayCard(FINDUS, Uno, 0)""
        assertThat(gameObserverTestSpy.getLastMethodCalled(), is("onPlayCard(FINDUS, Uno, 0)"));
    }

    @Test
    public void shouldReturnRightParametersOnChangeTurnTo(){
        //Given a game and gameObserver initialized in beforeEach()

        //When Findus ends his first turn
        game.endTurn();

        //Then the last method called in GameObserverTestSpy is "onChangeTurnTo(PEDDERSEN)"
        assertThat(gameObserverTestSpy.getLastMethodCalled(), is("onChangeTurnTo(PEDDERSEN)"));
    }

    @Test
    public void shouldReturnRightParametersOnAttackCard(){
        //Given a game and gameObserver initialized in beforeEach()

        //When Findus attacks Peddersens dos card, using uno
        Card unoCard = game.getCardInHand(Player.FINDUS, 2);
        game.playCard(Player.FINDUS, unoCard, 0);
        game.endTurn();

        Card dosCard = game.getCardInHand(Player.PEDDERSEN, 2);
        game.playCard(Player.PEDDERSEN, dosCard, 0);
        game.endTurn();

        game.attackCard(Player.FINDUS, unoCard, dosCard);

        //Then the last method called in GameObserverTestSpy is "onAttackCard(FINDUS, Uno, Dos)"
        assertThat(gameObserverTestSpy.getLastMethodCalled(), is("onAttackCard(FINDUS, Uno, Dos)"));
    }

    @Test
    public void shouldReturnRightParametersOnAttackHeroIsCalled(){
        //Given a game and gameObserver initialized in beforeEach()

        //When Findus Uno attacks Peddersens hero
        Card unoCard = game.getCardInHand(Player.FINDUS, 2);
        game.playCard(Player.FINDUS, unoCard, 0);
        game.endTurn();
        game.endTurn();
        game.attackHero(Player.FINDUS, unoCard);

        //Then the last method called in GameObserverTestSpy is "onAttackHero(FINDUS, Uno)"
        assertThat(gameObserverTestSpy.getLastMethodCalled(), is("onAttackHero(FINDUS, Uno)"));
    }

    @Test
    public void shouldReturnRightParametersOnUsePower(){
        //Given a game and gameObserver initialized in beforeEach()

        //When Findus uses his hero power in turn one
        game.usePower(Player.FINDUS);

        //Then the last method called in GameObserverTestSpy is "onUsePower(FINDUS)"
        assertThat(gameObserverTestSpy.getLastMethodCalled(), is("onUsePower(FINDUS)"));
    }

    @Test
    public void shouldReturnRightParametersOnCardDraw(){
        //Given a game and gameObserver initialized in beforeEach()

        //When Peddersen draws cuatro in turn two
        game.endTurn();

        //Then the last method called in GameObserverTestSpy is "onCardDraw(PEDDERSEN, Cuatro)"
        assertThat(gameObserverTestSpy.getAllMethodCalled().get(2), is("onCardDraw(PEDDERSEN, Cuatro)"));
    }

    @Test
    public void shouldReturnRightParametersOnCardRemove(){
        //Given a game and gameObserver initialized in beforeEach()

        //When Findus, in his second turn, attacks Peddeserns Uno card, using Dos
        Card cardDos = game.getCardInHand(Player.FINDUS, 1);
        game.playCard(Player.FINDUS, cardDos, 0);
        game.endTurn();

        Card cardUno = game.getCardInHand(Player.PEDDERSEN, 3);
        game.playCard(Player.PEDDERSEN, cardUno, 0);
        game.endTurn();

        game.attackCard(Player.FINDUS, cardDos, cardUno);

        //Then the last method called in GameObserverTestSpy is "onCardRemove(PEDDERSEN, Uno)"
        assertThat(gameObserverTestSpy.getAllMethodCalled().get(4), is("onCardRemove(PEDDERSEN, Uno)"));
    }

    @Test
    public void shouldReturnRightParametersOnGameWon(){
        //Given a game and gameObserver initialized in beforeEach()

        //When it is Findus 4th turn
        for(int i = 0; i < 8; i++) {
            game.endTurn();
        }

        //Then the last method called in GameObserverTestSpy is "onGameWon(FINDUS)"
        assertThat(gameObserverTestSpy.getAllMethodCalled().get(2), is("onGameWon(FINDUS)"));
    }

    @Test
    public void shouldReturnRightParametersForAttackCardOnCardUpdate(){
        //Given a game and gameObserver initialized in beforeEach()

        //When findus uses his uno minion to attack peddersens dos minion
        Card cardUno = game.getCardInHand(Player.FINDUS, 2);
        game.playCard(Player.FINDUS, cardUno, 0);
        game.endTurn();
        Card cardDos = game.getCardInHand(Player.PEDDERSEN, 2);
        game.playCard(Player.PEDDERSEN, cardDos, 0);
        game.endTurn();
        game.attackCard(Player.FINDUS, cardUno, cardDos);

        //Then onCardUpdate is called twice where card = uno and card = dos
        System.out.println(gameObserverTestSpy.getAllMethodCalled());
        assertThat(gameObserverTestSpy.getAllMethodCalled().get(4), is("onCardUpdate(Dos)")); //Dos takes damage
        assertThat(gameObserverTestSpy.getAllMethodCalled().get(2), is("onCardUpdate(Uno)")); //Uno takes damage
        assertThat(gameObserverTestSpy.getAllMethodCalled().get(1), is("onCardUpdate(Uno)")); //Uno becomes inactive
    }

    @Test
    public void shouldReturnRightParameterForEndTurnOnCardUpdate(){
        //Given a game and gameObserver initialized in beforeEach()

        //When findus plays uno and it becomes his turn again
        Card cardUno = game.getCardInHand(Player.FINDUS, 2);
        game.playCard(Player.FINDUS, cardUno, 0);
        game.endTurn();
        game.endTurn();

        //Then onCardUpdate is called with parameter Uno, (from setting Uno Active in endTurn())
        assertThat(gameObserverTestSpy.getAllMethodCalled().get(3), is("onCardUpdate(Uno)"));
    }

    @Test
    public void shouldReturnRightParameterForAttackHeroOnCardUpdate(){
        //Given a game and gameObserver initialized in beforeEach()

        //When findus plays uno and he attacks Peddersens hero
        Card cardUno = game.getCardInHand(Player.FINDUS, 2);
        game.playCard(Player.FINDUS, cardUno, 0);
        game.endTurn();
        game.endTurn();
        game.attackHero(Player.FINDUS, cardUno);

        //Then onCardUpdate is called with parameter Uno, (from setting Uno inactive in attackHero())
        assertThat(gameObserverTestSpy.getAllMethodCalled().get(1), is("onCardUpdate(Uno)"));
    }

    @Test
    public void shouldReturnRightParametersForEndTurnOnHeroUpdate(){
        //Given a game and gameObserver initialized in beforeEach()

        //When findus plays uno card and ends his turn, and then his second turn begins
        Card cardUno = game.getCardInHand(Player.FINDUS, 2);
        game.playCard(Player.FINDUS, cardUno, 0);
        game.endTurn();
        game.endTurn();

        //Then onHeroUpdate is called where who = findus
        assertThat(gameObserverTestSpy.getAllMethodCalled().get(1), is("onHeroUpdate(FINDUS)"));
    }

    @Test
    public void shouldReturnRightParametersForPlayCardOnHeroUpdate(){
        //Given a game and gameObserver initialized in beforeEach()

        //When findus plays uno card
        Card cardUno = game.getCardInHand(Player.FINDUS, 2);
        game.playCard(Player.FINDUS, cardUno, 0);

        //Then onHeroUpdate is called where who = findus
        assertThat(gameObserverTestSpy.getAllMethodCalled().get(1), is("onHeroUpdate(FINDUS)"));
    }

    @Test
    public void shouldReturnRightParameterForChangeHeroHealthOnHeroUpdate(){
        //Given a game and gameObserver initialized in beforeEach()

        //When findus plays uno and he attacks Peddersens hero
        Card cardUno = game.getCardInHand(Player.FINDUS, 2);
        game.playCard(Player.FINDUS, cardUno, 0);
        game.endTurn();
        game.endTurn();
        game.attackHero(Player.FINDUS, cardUno); //changeHeroHealth is called here

        //Then onHeroUpdate is called with parameter who = PEDDERSEN, as he looses health
        assertThat(gameObserverTestSpy.getAllMethodCalled().get(2), is("onHeroUpdate(PEDDERSEN)"));
    }

    @Test
    public void shouldReturnRightParameterForUsePowerOnHeroUpdate() {
        //Given a game and gameObserver initialized in beforeEach()

        //When Findus uses hero power
        game.usePower(Player.FINDUS);

        //Then onHeroUpdate is called with parameter who = FINDUS, as he uses mana
        assertThat(gameObserverTestSpy.getAllMethodCalled().get(1), is("onHeroUpdate(FINDUS)"));
    }

    @Test
    public void shouldNotNotifyUsePowerWhenFindusDoesNotHaveEnoughMana(){
        //Given a game and gameObserver initialized in beforeEach()

        //When Findus plays dos card and afterward tries to use his power
        Card card = game.getCardInHand(Player.FINDUS, 1);
        game.playCard(Player.FINDUS, card, 0);
        game.usePower(Player.FINDUS);

        //Then onUsePower is NOT called
        assertThat(gameObserverTestSpy.getAllMethodCalled().contains("onUsePower(FINDUS)"), is(false));
    }

    @Test
    public void shouldNotNotifyOnGameWonInFindusSecondTurnWhenNothingHasHappened(){
        //Given a game and gameObserver initialized in beforeEach()

        //When it becomes findus second turn
        game.endTurn();
        game.endTurn();

        //Then onGameWon is NOT called
        assertThat(gameObserverTestSpy.getAllMethodCalled().contains("onGameWon(FINDUS)"), is(false));
        assertThat(gameObserverTestSpy.getAllMethodCalled().contains("onGameWon(PEDDERSEN)"), is(false));
    }
}
