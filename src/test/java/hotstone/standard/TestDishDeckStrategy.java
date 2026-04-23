package hotstone.standard;

import hotstone.framework.Card;
import hotstone.framework.MutableCard;
import hotstone.framework.Player;
import hotstone.variants.deckStrategy.DishDeckStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;


public class TestDishDeckStrategy {
    private DishDeckStrategy dishDeckStrategy;
    private ArrayList<MutableCard> deck;

    @BeforeEach
    public void setUp() {
        dishDeckStrategy = new DishDeckStrategy();
        deck = dishDeckStrategy.buildDeck(Player.FINDUS);
    }

    @Test
    public void shouldHave18CardInDishDeck() {
        //Given dish deck initialized in beforeEach() method

        //Then there are 18 cards in deck
        assertThat(deck.size(), is(18));
    }

    @Test
    public void shouldHaveTwoOfEachCardFromDishDeck() {
        //Given dish deck initialized in beforeEach() method

        //Then there are two of each card from dish deck
        assertThat(deck.size(), is(18));

        assertThat(deck.stream()
                        .filter(card -> card.getName().equals(GameConstants.BROWN_RICE_CARD))
                        .filter(card -> card.getManaCost() == 1)
                        .filter(card -> card.getAttack() == 1)
                        .filter(card -> card.getHealth() == 2)
                        .count(),
                is(2L));

        assertThat(deck.stream()
                        .filter(card -> card.getName().equals(GameConstants.FRENCH_FRIES_CARD))
                        .filter(card -> card.getManaCost() == 1)
                        .filter(card -> card.getAttack() == 2)
                        .filter(card -> card.getHealth() == 1)
                        .count(),
                is(2L));
        assertThat(deck.stream()
                        .filter(card -> card.getName().equals(GameConstants.GREEN_SALAD_CARD))
                        .filter(card -> card.getManaCost() == 2)
                        .filter(card -> card.getAttack() == 2)
                        .filter(card -> card.getHealth() == 3)
                        .count(),
                is(2L));
        assertThat(deck.stream()
                        .filter(card -> card.getName().equals(GameConstants.TOMATO_SALAD_CARD))
                        .filter(card -> card.getManaCost() == 2)
                        .filter(card -> card.getAttack() == 3)
                        .filter(card -> card.getHealth() == 2)
                        .count(),
                is(2L));
        assertThat(deck.stream()
                        .filter(card -> card.getName().equals(GameConstants.POKE_BOWL_CARD))
                        .filter(card -> card.getManaCost() == 3)
                        .filter(card -> card.getAttack() == 2)
                        .filter(card -> card.getHealth() == 4)
                        .count(),
                is(2L));
        assertThat(deck.stream()
                        .filter(card -> card.getName().equals(GameConstants.PUMPKIN_SOUP_CARD))
                        .filter(card -> card.getManaCost() == 4)
                        .filter(card -> card.getAttack() == 2)
                        .filter(card -> card.getHealth() == 7)
                        .count(),
                is(2L));
        assertThat(deck.stream()
                        .filter(card -> card.getName().equals(GameConstants.NOODLE_SOUP_CARD))
                        .filter(card -> card.getManaCost() == 4)
                        .filter(card -> card.getAttack() == 5)
                        .filter(card -> card.getHealth() == 3)
                        .count(),
                is(2L));
        assertThat(deck.stream()
                        .filter(card -> card.getName().equals(GameConstants.SPRING_ROLLS_CARD))
                        .filter(card -> card.getManaCost() == 5)
                        .filter(card -> card.getAttack() == 3)
                        .filter(card -> card.getHealth() == 7)
                        .count(),
                is(2L));
        assertThat(deck.stream()
                        .filter(card -> card.getName().equals(GameConstants.BAKED_SALMON_CARD))
                        .filter(card -> card.getManaCost() == 5)
                        .filter(card -> card.getAttack() == 8)
                        .filter(card -> card.getHealth() == 2)
                        .count(),
                is(2L));
    }

    @Test
    public void shouldCost1ManaForFirstCardInDeck() {
        //Given dish deck initialized in beforeEach() method

        //When we get the top card in deck
        Card card = deck.get(0);

        //Then it cost 1 mana
        assertThat(card.getManaCost(), is(1));
    }

    @Test
    public void shouldCost2OrLessManaForSecondCardInDeck() {
        //Given dish deck initialized in beforeEach() method

        //When we get the second card in deck
        Card card = deck.get(1);

        //Then it cost 2 or less mana
        assertThat(card.getManaCost(), lessThanOrEqualTo(2));
    }


    @Test
    public void shouldCost4OrLessManaForThirdCardInDeck() {
        //Given dish deck initialized in beforeEach() method

        //When we get the third card in deck
        Card card = deck.get(2);

        //Then it cost 2 or less mana
        assertThat(card.getManaCost(), lessThanOrEqualTo(4));
    }


    @Test
    public void shouldHaveChance50_50ForBrownRice_FrenchFriesForFirstCardFor10000Builds() {
        //Given dish deck initialized in beforeEach() method

        //When we get the first card in deck 10000 times
        ArrayList<MutableCard> deckList;
        Card card;
        double brownRiceCount = 0; //uses double in the closeTo() in the assert statement
        double frenchFriesCount = 0;

        for (int i = 0; i < 10000; i++) {
            deckList = dishDeckStrategy.buildDeck(Player.FINDUS); //Player is irrelevant here
            card = deckList.get(0);
            if (card.getName().equals(GameConstants.BROWN_RICE_CARD)) {
                brownRiceCount++;
            } else if (card.getName().equals(GameConstants.FRENCH_FRIES_CARD)) {
                frenchFriesCount++;
            }
        }

        //Then there is a 50% chance it is Brown Rice and a 50% chance it is FrenchFries
        assertThat(brownRiceCount, closeTo(5000.0, 150.0));//Should be 50% of the 10000, with +/- 100 tolerance
        assertThat(frenchFriesCount, closeTo(5000.0, 150.0));
        assertThat(brownRiceCount + frenchFriesCount, is(10000.0)); //Check that there are no other cards than brown rice and french fries
    }

    @Test
    public void shouldHaveBrownRice_FrenchFries_GreenSalad_TomatoSaladAtLeastOnceForSecondCardFor100Builds() {
        //Given dish deck initialized in beforeEach() method

        //When we get the second card in deck 100 times
        ArrayList<MutableCard> deckList;
        Card card;
        int brownRiceCount = 0;
        int frenchFriesCount = 0;
        int greenSaladCount = 0;
        int tomatoSaladCount = 0;
        int wrongCardCount = 0;

        for (int i = 0; i < 10000; i++) {
            deckList = dishDeckStrategy.buildDeck(Player.FINDUS); //Player is irrelevant here
            card = deckList.get(1);
            if (card.getName().equals(GameConstants.BROWN_RICE_CARD)) {
                brownRiceCount++;
            } else if (card.getName().equals(GameConstants.FRENCH_FRIES_CARD)) {
                frenchFriesCount++;
            } else if (card.getName().equals(GameConstants.GREEN_SALAD_CARD)) {
                greenSaladCount++;
            } else if (card.getName().equals(GameConstants.TOMATO_SALAD_CARD)) {
                tomatoSaladCount++;
            } else {
                wrongCardCount++;
            }
        }

        //Then Brown Rice, FrenchFries, GreenSalad and TomatoSalad show up at least once
        assertThat(wrongCardCount, is(0));
        assertThat(brownRiceCount, greaterThanOrEqualTo(1));
        assertThat(frenchFriesCount, greaterThanOrEqualTo(1));
        assertThat(greenSaladCount, greaterThanOrEqualTo(1));
        assertThat(tomatoSaladCount, greaterThanOrEqualTo(1));
    }

    @Test
    public void shouldHaveBrownRice_FrenchFries_GreenSalad_TomatoSalad_PokeBowl_PumpkinSoup_NoodleSoupAtLeastOnceForThirdCardFor100Builds() {
        //Given dish deck initialized in beforeEach() method

        //When we get the second card in deck 100 times
        ArrayList<MutableCard> deckList;
        Card card;
        int brownRiceCount = 0;
        int frenchFriesCount = 0;
        int greenSaladCount = 0;
        int tomatoSaladCount = 0;
        int pokeBowlCount = 0;
        int pumpkinSoupCount = 0;
        int noodleSoupCount = 0;
        int wrongCardCount = 0;

        for (int i = 0; i < 1000; i++) {
            deckList = dishDeckStrategy.buildDeck(Player.FINDUS); //Player is irrelevant here
            card = deckList.get(2);
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
            } else {
                wrongCardCount++;
            }
        }

        //Then Brown Rice, FrenchFries, GreenSalad and TomatoSalad show up at least once
        assertThat(wrongCardCount, is(0));
        assertThat(brownRiceCount, greaterThanOrEqualTo(1));
        assertThat(frenchFriesCount, greaterThanOrEqualTo(1));
        assertThat(greenSaladCount, greaterThanOrEqualTo(1));
        assertThat(tomatoSaladCount, greaterThanOrEqualTo(1));
        assertThat(pokeBowlCount, greaterThanOrEqualTo(1));
        assertThat(pumpkinSoupCount, greaterThanOrEqualTo(1));
        assertThat(noodleSoupCount, greaterThanOrEqualTo(1));
    }

    @Test
    public void shouldHaveAllCardsFor18thCardFor100BuildsAtLeastOnce() {
        //Given dish deck initialized in beforeEach() method

        //When we get the 18th card in deck 100 times. 18 is just a card that don't have conditions. We could also use 4-17
        ArrayList<MutableCard> deckList;
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
            deckList = dishDeckStrategy.buildDeck(Player.FINDUS); //Player is irrelevant here
            card = deckList.get(17);
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
