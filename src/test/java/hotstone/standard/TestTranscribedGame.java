package hotstone.standard;

import hotstone.decorator.TranscriptedDecoratorHotStoneGame;
import hotstone.factories.AlphaStoneFactory;
import hotstone.factories.GammaStoneFactory;
import hotstone.factories.HotStoneFactory;
import hotstone.framework.Card;
import hotstone.framework.MutableGame;
import hotstone.framework.Player;
import org.junit.jupiter.api.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import hotstone.framework.Game;

public class TestTranscribedGame {
    MutableGame realGame;
    MutableGame transsribedGame;
    Game game;

    public TestTranscribedGame(){
        realGame = new StandardHotStoneGame(new AlphaStoneFactory());
        transsribedGame = new TranscriptedDecoratorHotStoneGame(realGame);
    }

    @Test
    public void manualDecoratorTest(){
        game = transsribedGame;
        Card unoCard = game.getCardInHand(Player.FINDUS, 2);
        game.playCard(Player.FINDUS, unoCard, 0);
        game.endTurn();

        Card dosCard = game.getCardInHand(Player.PEDDERSEN, 2);
        game.playCard(Player.PEDDERSEN, dosCard, 0);
        game.endTurn();

        game.usePower(Player.FINDUS);
        game.attackCard(Player.FINDUS, unoCard, dosCard);
        game.endTurn();

        game.attackHero(Player.PEDDERSEN, dosCard);

        game = realGame; //... Further methods produce no transcription
        game.endTurn();
    }
}
