package hotstone.standard;

import hotstone.factories.DeltaStoneFactory;
import hotstone.factories.HotStoneFactory;
import hotstone.framework.Card;
import hotstone.framework.Player;
import org.junit.jupiter.api.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

import hotstone.framework.Game;

public class TestDeltaStone {
    private Game game;
    private HotStoneFactory deltaStoneFactory;

    /** Fixture for DeltaStone testing.  */

    @BeforeEach
    public void setUp() {
        deltaStoneFactory = new DeltaStoneFactory();
        game = new StandardHotStoneGame(deltaStoneFactory);
    }

    @Test
    public void shouldHave5ManaForFindusInFirstTurn(){
        //Given game initialized in beforeEach() method

        //When game starts

        //Then Findus has 5 mana
        assertThat(game.getHero(Player.FINDUS).getMana(), is(5));
    }

    @Test
    public void shouldHaveAllCardsAppearAtLeastOnceInPeddersensDrawnCardInTurn2For100Builds() {
        //This is similar to another test in TestDishDeckStrategy class, but this is an integration test
        //Given game initialized in beforeEach() method

        //When we get the 8th card in deck 100 times.
        Card card;
        int brownRiceCount = 0;
        int frenchFriesCount = 0;
        int greenSaladCount = 0;
        int tomatoSaladCount = 0;
        int pokeBowlCount = 0;
        int pumpkinSoupCount = 0;
        int noodleSoupCount = 0;
        int springRollsCount = 0;
        int bakedSalmonCount = 0;

        for (int i = 0; i < 100; i++) {
            //Creates a new game and therefore a new deck order
            game = new StandardHotStoneGame(deltaStoneFactory);
            game.endTurn(); //Peddersens turn where he draws a card. This card should be random
            card = game.getCardInHand(Player.PEDDERSEN, 0);
            if (card.getName().equals(GameConstants.BROWN_RICE_CARD)) {
                brownRiceCount++;
            } else if (card.getName().equals(GameConstants.FRENCH_FRIES_CARD)) {
                frenchFriesCount++;
            } else if (card.getName().equals(GameConstants.GREEN_SALAD_CARD)) {
                greenSaladCount++;
            } else if (card.getName().equals(GameConstants.TOMATO_SALAD_CARD)) {
                tomatoSaladCount++;
            } else if (card.getName().equals(GameConstants.POKE_BOWL_CARD)) {
                pokeBowlCount++;
            } else if (card.getName().equals(GameConstants.PUMPKIN_SOUP_CARD)) {
                pumpkinSoupCount++;
            } else if (card.getName().equals(GameConstants.NOODLE_SOUP_CARD)) {
                noodleSoupCount++;
            } else if(card.getName().equals(GameConstants.SPRING_ROLLS_CARD)){
                springRollsCount++;
            } else {
                bakedSalmonCount++;
            }
        }

        //Then all cards show up at least once
        assertThat(brownRiceCount, greaterThanOrEqualTo(1));
        assertThat(frenchFriesCount, greaterThanOrEqualTo(1));
        assertThat(greenSaladCount, greaterThanOrEqualTo(1));
        assertThat(tomatoSaladCount, greaterThanOrEqualTo(1));
        assertThat(pokeBowlCount, greaterThanOrEqualTo(1));
        assertThat(pumpkinSoupCount, greaterThanOrEqualTo(1));
        assertThat(noodleSoupCount, greaterThanOrEqualTo(1));
        assertThat(springRollsCount, greaterThanOrEqualTo(1));
        assertThat(bakedSalmonCount, greaterThanOrEqualTo(1));
    }
}
