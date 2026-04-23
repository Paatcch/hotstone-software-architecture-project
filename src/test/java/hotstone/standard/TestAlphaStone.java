/*
 * Copyright (C) 2022 - 2025. Henrik Bærbak Christensen, Aarhus University.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package hotstone.standard;

/**
 * Skeleton class for AlphaStone test cases
 * <p>
 * This source code is from the book
 * "Flexible, Reliable Software:
 * Using Patterns and Agile Development"
 * 2nd Edition
 * Author:
 * Henrik Bærbak Christensen
 * Department of Computer Science
 * Aarhus University
 */

import hotstone.factories.AlphaStoneFactory;
import hotstone.factories.HotStoneFactory;
import hotstone.framework.*;
import hotstone.variants.EffectStategy.NoEffectStrategy;
import org.junit.jupiter.api.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/** Template for your own ongoing TDD process.
 * Fill it out until you have covered all
 * requirements for the alpha stone game.
 */
public class TestAlphaStone {
    private Game game;
    private HotStoneFactory alphaStoneFactory;
    private Effect noEffectStrategy;

    /** Fixture for AlphaStone testing. */
    @BeforeEach
    public void setUp() {
        alphaStoneFactory = new AlphaStoneFactory();
        game = new StandardHotStoneGame(alphaStoneFactory);
        noEffectStrategy = new NoEffectStrategy();
    }

    // Example of an early, simple test case:
    // Turn handling
    @Test
    public void shouldHaveFindusAsFirstPlayer() {
        // Given a game, initialized in 'beforeeach' method

        // When I ask for the player in turn
        Player player = game.getPlayerInTurn();
        // Then it should be Findus
        assertThat(player, is(Player.FINDUS));

        // NB: during development you can use the
        // following 'print full game state' helper
        // function, to ensure your assumptions
        // on game state is correct.
        // TestHelper.printGameState(game);
    }

    @Test
    public void shouldHavePeddersenAsPlayerWhenFindusEndsTurn() {
        // Given a game, initialized in 'beforeeach' method

        //When Findus ends his turn
        assertThat(game.getPlayerInTurn(), is(Player.FINDUS));
        game.endTurn();

        //Then it is Peddersens turn
        assertThat(game.getPlayerInTurn(), is(Player.PEDDERSEN));
    }

    @Test
    public void cardDosHasAttributesDos222() {
        // Given a card with attributes Dos, 2, 2, 2
        //When card Dos is initialized
        StandardCard card = new StandardCard(GameConstants.DOS_CARD, 2,2,2, null, noEffectStrategy, ""); //Player owner is null, as it is irrelevant for this test

        //Then card Dos has attributes (Dos, 2, 2, 2)
        assertThat(card.getName(), is("Dos"));
        assertThat(card.getManaCost(), is(2));
        assertThat(card.getAttack(), is(2));
        assertThat(card.getHealth(), is(2));
    }

    @Test
    public void cardTresHasAttributesTres333() {
        // Given a card with attributes Tres, 3, 3, 3
        // When card Tres is initialized
        StandardCard card = new StandardCard(GameConstants.TRES_CARD, 3,3,3, null, noEffectStrategy, ""); //Player owner is null, as it is irrelevant for this test

        //Then card Tres has attributes (Tres, 3, 3, 3)
        assertThat(card.getName(), is("Tres"));
        assertThat(card.getManaCost(), is(3));
        assertThat(card.getAttack(), is(3));
        assertThat(card.getHealth(), is(3));
    }


    // Example of a later, more complex, test case:
    // Card handling

    // The HotStone specs are quite insisting on how
    // the cards, drawn from the deck, are organized
    // in the hand. So when drawing the top three cards
    // from the deck (uno, dos, tres) they have to
    // be organized in the hand as
    // index 0 = tres; index 1 = dos; index 2 = uno
    // That is, a newly drawn card is 'at the top'
    // of the hand - always entered at position 0
    // and pushing the rest of the cards 1 position
    // 'down'
    @Test
    public void shouldHaveUnoDosTresCardsInitially() {

        // Given a game, Findus has 3 cards in hand
        int count = game.getHandSize(Player.FINDUS);
        assertThat(count, is(3));
        // And these are ordered Tres, Dos, Uno in slot 0,1,2

        // When I pick card 0
        Card card = game.getCardInHand(Player.FINDUS, 0);
        // Then is it Tres
        assertThat(card.getName(), is(GameConstants.TRES_CARD));

        // When I pick card 1
        card = game.getCardInHand(Player.FINDUS, 1);
        // Then is it Dos
        assertThat(card.getName(), is(GameConstants.DOS_CARD));

        // When I pick card 2
        card = game.getCardInHand(Player.FINDUS, 2);
        // Then is it Uno
        assertThat(card.getName(), is(GameConstants.UNO_CARD));

    }


    @Test
    public void shouldHaveFourCardsInHandAtFindusSecondTurn(){
        // Given a game, initialized in 'beforeeach' method

        //When it's Findus' second turn
        game.endTurn(); //End Findus first turn, and make it Peddersens turn
        game.endTurn(); //End Peddersens first turn and make it Findus second turn

        //Then Findus's handsize is four
        assertThat(game.getHandSize(Player.FINDUS), is(4));
    }

    @Test
    public void shouldHaveFourCardsInHandAtPeddersensFirstTurn(){ //This was shown in the video demonstration
        // Given a game, initialized in 'beforeeach' method

        //When it's Peddersens second turn
        game.endTurn(); //End Findus first turn, and make it Peddersens first turn

        //Then Peddersens handsize is four
        assertThat(game.getHandSize(Player.PEDDERSEN), is(4));
    }

    @Test
    public void shouldHaveCardCuatroAtIndex0WhenFindusSecondTurn(){
        //Given game initialized by beforeEach()

        //When it is Findus second turn
        game.endTurn();
        game.endTurn();

        //Then Findus has card cuatro at index 0 in his hand
        Card card = game.getCardInHand(Player.FINDUS, 0);
        assertThat(card.getName(), is(GameConstants.CUATRO_CARD));
    }

    @Test
    public void shouldHaveCardCuatroAtIndex0WhenPeddersensFirstTurn(){
        //Given game initialized by beforeEach()

        //When it is Peddersens first turn
        game.endTurn();

        //Then Peddersen has card cuatro at index 0 in his hand
        Card card = game.getCardInHand(Player.PEDDERSEN, 0);
        assertThat(card.getName(), is(GameConstants.CUATRO_CARD));
    }

    @Test
    public void shouldHaveCincoInHandAtIndex0DrawnFromDeckWhenFindusThirdTurn(){
        //Given game initialized by beforeEach()

        //When it is Findus third turn
        game.endTurn();
        game.endTurn();
        game.endTurn();
        game.endTurn();

        //Then Findus have Cinco in hand at index 0, since he have just drawn it from his deck
        assertThat(game.getCardInHand(Player.FINDUS,0).getName(), is(GameConstants.CINCO_CARD));
    }

    @Test
    public void shouldHaveHandSize4WhenPeddersensFirstTurnAndFindusShouldHaveHandsize3(){
        //Given game initialized by beforeEach()

        //When it is Peddersens first turn
        game.endTurn();

        //Then Peddersens handsize is 4 and Finduses handsize is 3
        assertThat(game.getHandSize(Player.PEDDERSEN), is(4));
        assertThat(game.getHandSize(Player.FINDUS), is(3));
    }

    @Test
    public void shouldHaveCincoInHandAtIndex0DrawnFromDeckWhenPeddersensSecondTurn(){
        //Given game initialized by beforeEach()

        //When it is Pedersens second turn
        game.endTurn(); //Includes drawCard()
        game.endTurn();
        game.endTurn();

        //Then Peddersen have Cinco in hand at index 0, since he have just drawn it from his deck
        assertThat(game.getCardInHand(Player.PEDDERSEN,0).getName(), is(GameConstants.CINCO_CARD));
    }

    @Test
    public void shouldStillHave3CardsInHandForPeddersenWhenFindusPlaysCard(){
        //Given game initialized by beforeEach()

        //When Findus plays card
        Card cardToBePlayed = game.getCardInHand(Player.FINDUS, 0);
        game.playCard(Player.FINDUS, cardToBePlayed, 0);

        //Then Peddersen still has 3 card in his hand
        assertThat(game.getHandSize(Player.PEDDERSEN), is(3));
    }

    @Test
    public void shouldHave2CardsLeftInHandForFindusWhenHePlaysCardFirstTurn(){
        //Given game initialized by beforeEach()

        //When Findus plays card the first turn
        Card cardToBePlayed = game.getCardInHand(Player.FINDUS, 0);
        game.playCard(Player.FINDUS, cardToBePlayed, 0);

        //Then Findus has 2 cards in his hand
        assertThat(game.getHandSize(Player.FINDUS), is(2));
    }

    @Test
    public void shouldHave3CardsLeftInHandForPeddersenWhenHePlaysCardHisFirstTurn(){
        //Given game initialized by beforeEach()

        //When Peddersen plays a card his first turn
        game.endTurn(); //Peddersens first turn
        Card cardToBePlayed = game.getCardInHand(Player.PEDDERSEN, 0);
        game.playCard(Player.PEDDERSEN, cardToBePlayed, 0);

        //Then Peddersen has 3 cards in his hand
        assertThat(game.getHandSize(Player.PEDDERSEN), is(3));
    }

    @Test
    public void shouldOnlyHaveUnoAndTresInFindusHandWhenFindusPlaysDosInFirstTurn(){
        //Given game initialized by beforeEach()

        //When Findus plays Dos in his first turn
        Card cardToBePlayed = game.getCardInHand(Player.FINDUS, 1); //Dos
        game.playCard(Player.FINDUS, cardToBePlayed, 0); //Card is played at index 0

        //Then Findus' hand only includes Uno and Tres
        Card cardUno = game.getCardInHand(Player.FINDUS, 1);
        Card cardTres = game.getCardInHand(Player.FINDUS, 0);

        assertThat(cardUno.getName(), is(GameConstants.UNO_CARD));
        assertThat(cardTres.getName(), is(GameConstants.TRES_CARD));
        assertThat(game.getHandSize(Player.FINDUS), is(2));
    }

    @Test
    public void shouldHaveDosCardInFieldForFindusWhenHePlaysDos(){
        //Given game initialized by beforeEach()

        //When Findus plays the Dos card
        Card cardToBePlayed = game.getCardInHand(Player.FINDUS, 1);
        game.playCard(Player.FINDUS, cardToBePlayed, 0);

        //Then the dos card should be in findus field
        assertThat(game.getCardInField(Player.FINDUS, 0), is(cardToBePlayed));
    }

    @Test
    public void shouldOnlyHaveUnoDosAndTresInPeddersenHandWhenPedddersenPlaysCuatroInFirstTurn(){
        //Given game initialized by beforeEach()

        //When Peddersen plays Cuatro in his first turn
        game.endTurn();
        Card cardToBePlayed = game.getCardInHand(Player.PEDDERSEN, 0); //Cuatro
        game.playCard(Player.PEDDERSEN, cardToBePlayed, 0); //Card is played at index 0

        //Then Peddersens hand only includes Uno and Tres
        Card cardUno = game.getCardInHand(Player.PEDDERSEN, 2);
        Card cardDos = game.getCardInHand(Player.PEDDERSEN, 1);
        Card cardTres = game.getCardInHand(Player.PEDDERSEN, 0);

        assertThat(cardUno.getName(), is(GameConstants.UNO_CARD));
        assertThat(cardDos.getName(), is(GameConstants.DOS_CARD));
        assertThat(cardTres.getName(), is(GameConstants.TRES_CARD));
        assertThat(game.getHandSize(Player.PEDDERSEN), is(3));
    }

    @Test
    public void shouldHaveCuatroCardInFieldForPeddersenWhenHePlaysCuatro(){
        //Given game initialized by beforeEach()

        //When Peddersen plays the Cuatro card
        game.endTurn();
        Card cardToBePlayed = game.getCardInHand(Player.PEDDERSEN, 0); //Cuatro card
        game.playCard(Player.PEDDERSEN, cardToBePlayed, 0);

        //Then the dos card should be in Peddersens field
        assertThat(game.getCardInField(Player.PEDDERSEN, 0), is(cardToBePlayed));
    }

    @Test
    public void shouldHaveFieldSize1WhenFindusPlaysUno(){
        //Given game initialized by beforeEach()

        //When findus plays uno
        Card cardUnoToBePlayed = game.getCardInHand(Player.FINDUS,2);
        game.playCard(Player.FINDUS, cardUnoToBePlayed, 0);

        //Then findus' field size should be 1
        assertThat(game.getFieldSize(Player.FINDUS), is(1));
    }

    @Test
    public void shouldHaveFieldSize2ForPeddersenAfterHeHasPlayedTwoCards(){
        //Given game initialized by beforeEach()

        //When Peddersen has played two cards
        game.endTurn();
        Card unoToPlay = game.getCardInHand(Player.PEDDERSEN, 3);
        game.playCard(Player.PEDDERSEN, unoToPlay, 0);

        game.endTurn(); //Findus turn
        game.endTurn(); // Peddersens turn - this way Peddersen recieves more mana so this test works in later iterations

        Card dosToPlay = game.getCardInHand(Player.PEDDERSEN, 3);
        game.playCard(Player.PEDDERSEN, dosToPlay, 0);

        //Then Peddersen should have field size of 2
        assertThat(game.getFieldSize(Player.PEDDERSEN), is(2));
    }

    @Test
    public void shouldHaveFindusAsWinnerAfterFourRounds(){
        //Given game initialized by beforeEach()

        //When four rounds have passed
        for(int i = 0; i < 8; i++){
            game.endTurn();
        }

        //Then findus is winner of the game
        assertThat(game.getWinner(), is(Player.FINDUS));
    }

    @Test
    public void shouldHaveTurnNumberThreeAtFindusSecondTurn(){
        //Given game initialized by beforeEach()

        //When it is findus second turn
        game.endTurn();
        game.endTurn();

        //Then turn number should be three
        assertThat(game.getTurnNumber(), is(3)); //ASK studiecafe - is it too simple? just implement?
    }
    @Test
    public void shouldHaveTurnNumberFourAtPeddersensSecondTurn(){
        //Given game initialized by beforeEach()

        //When it is peddersens second turn
        game.endTurn();
        game.endTurn();
        game.endTurn();

        //Then turn number should be four
        assertThat(game.getTurnNumber(), is(4));
    }

    @Test
    public void shouldHaveNoWinnerAfterThreeRounds(){
        //Given game initialized by beforeEach()

        //When three rounds have passed
        for(int i = 0; i < 6; i++){
            game.endTurn();
        }

        //Then there is no winner
        assertThat(game.getWinner(), is(nullValue()));
    }

    @Test
    public void shouldHaveTypeBabyForFindusHero(){
        //Given game initialized by beforeEach()

        //When game starts

        //Then Findus hero have type "Baby"
        assertThat(game.getHero(Player.FINDUS).getType(), is(GameConstants.BABY_HERO_TYPE));
    }

    @Test
    public void shouldHaveTypeBabyForPeddersensHero(){
        //Given game initialized by beforeEach()

        //When game starts

        //Then Peddersens hero have type "Baby"
        assertThat(game.getHero(Player.PEDDERSEN).getType(), is(GameConstants.BABY_HERO_TYPE));
    }

    /* ASK Study cafe - Hvad vil det sige at dens power er "Cute". Skal det returneres som status, eller er det bare en "ligegyldig" variabel?
    @Test
    public void shouldHaveHeroPowerCuteForFindusHero(){
        //Given game initialized by beforeEach()

        //When game starts

        //Then Findus hero have power "Cute"
        assertThat(game.getHero(Player.FINDUS).getPower(), is("cute"));
    }
     */

    @Test
    public void shouldHaveEffectDescriptionJustCuteForPeddersensHero(){
        //Given game initialized by beforeEach()

        //When game starts

        //Then Peddersens hero have effect description "Just Cute"
        assertThat(game.getHero(Player.PEDDERSEN).getEffectDescription(), is("Just Cute"));
    }

    @Test
    public void shouldHaveEffectDescriptionJustCuteForFindusHero(){
        //Given game initialized by beforeEach()

        //When game starts

        //Then Findus hero have effect description "Just Cute"
        assertThat(game.getHero(Player.FINDUS).getEffectDescription(), is("Just Cute"));
    }

    @Test
    public void shouldHave3ManaForFindusFirstTurn(){
        //Given game initialized by beforeEach()

        //When game starts

        //Then Findus have 3 mana crystals
        assertThat(game.getHero(Player.FINDUS).getMana(), is(3));
    }

    @Test
    public void shouldHave2ManaCrystalsForFindusAfterPlayingUnoAsFirstCard(){
        //Given game initialized by beforeEach()

        //When findus plays uno first
        Card cardToPlay = game.getCardInHand(Player.FINDUS,2);
        game.playCard(Player.FINDUS, cardToPlay, 0);

        //Then Findus has only two mana
        assertThat(game.getHero(Player.FINDUS).getMana(), is(2));
    }

    @Test
    public void shouldHave0ManaCrystalsForFindusAfterPlayingBothUnoAndDosInSameTurn(){
        //Given game initialized by beforeEach()

        //When findus plays both uno and dos in same turn
        Card cardUno = game.getCardInHand(Player.FINDUS, 2);
        Card cardDos = game.getCardInHand(Player.FINDUS, 1);
        game.playCard(Player.FINDUS, cardUno, 0);
        game.playCard(Player.FINDUS, cardDos, 0);

        //Then findus should have zero mana crystals left
        assertThat(game.getHero(Player.FINDUS).getMana(), is(0));
    }

    @Test
    public void shouldNotHaveTresInFieldWhenFindusPlaysUnoDosTresInFirstTurnAsHeDoesNotHaveEnoughMana(){
        //Given game initialized by beforeEach()

        //When findus plays both uno, dos and tres
        Card cardUno = game.getCardInHand(Player.FINDUS,2);
        Card cardDos = game.getCardInHand(Player.FINDUS,1);
        Card cardTres = game.getCardInHand(Player.FINDUS,0);

        game.playCard(Player.FINDUS, cardUno, 0);
        game.playCard(Player.FINDUS, cardDos, 0);
        game.playCard(Player.FINDUS, cardTres, 0);

        //Then the tres card should not be in field
        assertThat(game.getCardInField(Player.FINDUS, 0), is(not(cardTres)));
    }

    @Test
    public void shouldReturnStatusNOT_ENOUGH_MANAWhenFindusPlaysTresAndAlreadyHavePlayedUnoInTheSameTurn(){
        //Given game initialized by beforeEach()

        //When findus plays both uno and tres
        Card cardUno = game.getCardInHand(Player.FINDUS,2);
        game.playCard(Player.FINDUS, cardUno, 0);
        Card cardTres = game.getCardInHand(Player.FINDUS,0);

        //Then Status NOT_ENOUGH_MANA is returned
        assertThat(game.playCard(Player.FINDUS, cardTres,0), is(Status.NOT_ENOUGH_MANA));
    }

    @Test
    public void shouldHave0ManaCrystalsForPeddersenAfterPlayingBothUnoAndDosInSameTurn(){
        //Given game initialized by beforeEach()

        //When Peddersen plays both uno and dos in same turn
        game.endTurn();
        Card cardUno = game.getCardInHand(Player.PEDDERSEN, 3);
        Card cardDos = game.getCardInHand(Player.PEDDERSEN, 2);
        game.playCard(Player.PEDDERSEN, cardUno, 0);
        game.playCard(Player.PEDDERSEN, cardDos, 0);

        //Then Peddersen should have zero mana crystals left
        assertThat(game.getHero(Player.PEDDERSEN).getMana(), is(0));
    }

    @Test
    public void shouldReturnStatusNOT_ENOUGH_MANAWhenPeddersenPlaysTresAndAlreadyHavePlayedUnoInTheSameTurn(){
        //Given game initialized by beforeEach()

        //When Peddersen plays both uno and tres
        game.endTurn();
        Card cardUno = game.getCardInHand(Player.PEDDERSEN,3);
        game.playCard(Player.PEDDERSEN, cardUno, 0);
        Card cardTres = game.getCardInHand(Player.PEDDERSEN,1);

        //Then Status NOT_ENOUGH_MANA is returned
        assertThat(game.playCard(Player.PEDDERSEN, cardTres,0), is(Status.NOT_ENOUGH_MANA));
    }

    @Test
    public void shouldReturnStatusOkWhenFindusPlaysUno(){
        //Given game initialized by beforeEach()

        //When findus plays uno card
        //Then Status.OK is returned
        Card cardUno = game.getCardInHand(Player.FINDUS, 2);
        assertThat(game.playCard(Player.FINDUS, cardUno, 0), is(Status.OK));
    }

    @Test
    public void shouldReturnStatusOkWhenPeddersenPlaysUno(){
        //Given game initialized by beforeEach()

        //When peddersen plays uno card
        game.endTurn();

        //Then Status.OK is returned
        Card cardUno = game.getCardInHand(Player.PEDDERSEN, 3);
        assertThat(game.playCard(Player.PEDDERSEN, cardUno, 0), is(Status.OK));
    }

    @Test
    public void shouldHaveManaThreeAtBeginningOfSecondTurnAfterPlayingUnoFirstTurnForFindus(){
        //Given game initialized by beforeEach()

        //When Findus plays uno first turn, and his second turn starts
        Card cardUno = game.getCardInHand(Player.FINDUS, 2);
        game.playCard(Player.FINDUS, cardUno, 0);

        assertThat(game.getHero(Player.FINDUS).getMana(), is(2)); //Here mana is now two

        game.endTurn();
        game.endTurn(); //Findus second turn starts

        //Then Findus has three mana again at the start of the second turn
        assertThat(game.getHero(Player.FINDUS).getMana(), is(3));
    }

    @Test
    public void shouldHaveManaThreeAtBeginningOfSecondTurnAfterPlayingUnoFirstTurnForPeddersen(){
        //Given game initialized by beforeEach()

        //When Peddersen plays uno first turn, and his second turn starts
        game.endTurn();
        Card cardUno = game.getCardInHand(Player.PEDDERSEN, 3);
        game.playCard(Player.PEDDERSEN, cardUno, 0);

        assertThat(game.getHero(Player.PEDDERSEN).getMana(), is(2)); //Here mana is now two

        game.endTurn();
        game.endTurn(); //Peddersens second turn starts

        //Then Peddersen has three mana again at the start of the second turn
        assertThat(game.getHero(Player.PEDDERSEN).getMana(), is(3));
    }

    @Test
    public void shouldHaveHealth21ForFindusAndPeddersenAtBeginningOfGame(){
        //Given game initialized by beforeEach()

        //When game starts

        //Then Findus and Peddersen has health 21
        assertThat(game.getHero(Player.FINDUS).getHealth(), is(21));
        assertThat(game.getHero(Player.PEDDERSEN).getHealth(), is(21));
    }

    @Test
    public void shouldHave1ManaForFindusWhenHeHasUsedHeroPower(){
        //Given game initialized by beforeEach()

        //When Findus has used hero power
        game.usePower(Player.FINDUS);

        //Then Findus mana is 1
        assertThat(game.getHero(Player.FINDUS).getMana(), is(1));
    }

    @Test
    public void shouldHave1ManaForPeddersenWhenHeHasUsedHeroPower(){
        //Given game initialized by beforeEach()

        //When Peddersen has used hero power
        game.endTurn();
        game.usePower(Player.PEDDERSEN);

        //Then Peddersen mana is 1
        assertThat(game.getHero(Player.PEDDERSEN).getMana(), is(1));
    }

    @Test
    public void shouldReturnStatusNOT_ENOUGH_MANAWhenFindusTriesToUsePowerAfterPlayingDos(){
        //Given game initialized by beforeEach()

        //When Findus has played Dos and uses his power in the same turn
        Card card = game.getCardInHand(Player.FINDUS,1);
        game.playCard(Player.FINDUS, card,0);

        //Then Status NOT_ENOUGH_MANA is returned
        assertThat(game.usePower(Player.FINDUS), is(Status.NOT_ENOUGH_MANA));
    }

    @Test
    public void shouldReturnStatusOKWhenFindusUsesPower(){
        //Given game initialized by beforeEach()

        //When Findus uses power
        //Then Status OK is returned
        assertThat(game.usePower(Player.FINDUS), is(Status.OK));
    }

    @Test
    public void shouldReturnStatusOKWhenPeddersenUsesPower(){
        //Given game initialized by beforeEach()

        //When Peddersen uses power
        game.endTurn();

        //Then Status OK is returned
        assertThat(game.usePower(Player.PEDDERSEN), is(Status.OK));
    }

    @Test
    public void shouldStillHaveMana1WhenFindusTriesToUsePowerAfterPlayingDos(){
        //Given game initialized by beforeEach()

        //When Findus has played Dos and tries to use his power in the same turn
        Card card = game.getCardInHand(Player.FINDUS,1);
        game.playCard(Player.FINDUS, card,0);

        assertThat(game.getHero(Player.FINDUS).getMana(), is(1)); //Check that Findus' mana is 1
        game.usePower(Player.FINDUS); //Findus cant use power since he doesn't hav enough mana

        //Then Findus mana is still 1
        assertThat(game.getHero(Player.FINDUS).getMana(), is(1));
    }

    @Test
    public void shouldHaveHealth20ForPeddersenAfterFindusAttacksWithMinionUnoInThirdTurn(){
        //Given game initialized by beforeEach()

        //When findus's minion attacks peddersens hero
        Card cardUno = game.getCardInHand(Player.FINDUS, 2);
        game.playCard(Player.FINDUS, cardUno, 0);

        game.endTurn();
        game.endTurn(); // findus second turn

        game.attackHero(Player.FINDUS, cardUno);

        //Then peddersens hero has 20 health
        assertThat(game.getHero(Player.PEDDERSEN).getHealth(), is(20));
    }

    @Test
    public void shouldHaveHealth19ForFindusAfterPeddersenAttacksWithMinionDosInFourthTurn(){
        //Given game initialized by beforeEach()

        //When Peddersens minion attacks peddersens hero
        game.endTurn();
        Card cardDos = game.getCardInHand(Player.PEDDERSEN, 2);
        game.playCard(Player.PEDDERSEN, cardDos, 0);

        game.endTurn();
        game.endTurn(); // Peddersens second turn

        game.attackHero(Player.PEDDERSEN, cardDos);

        //Then Findus hero has 19 health
        assertThat(game.getHero(Player.FINDUS).getHealth(), is(19));
    }

    @Test
    public void shouldReturnStatusOKAfterFindusAttacksWithMinionUnoInThirdTurn(){
        //Given game initialized by beforeEach()

        //When findus's minion attacks peddersens hero
        Card cardUno = game.getCardInHand(Player.FINDUS, 2);
        game.playCard(Player.FINDUS, cardUno, 0);

        game.endTurn();
        game.endTurn(); // findus second turn

        //Then Status.OK is returned
        assertThat(game.attackHero(Player.FINDUS, cardUno), is(Status.OK));
    }

    @Test
    public void shouldReturnStatusOKWhenPeddersenAttacksWithMinionDosInFourthTurn(){
        //Given game initialized by beforeEach()

        //When Peddersens minion attacks peddersens hero
        game.endTurn();
        Card cardDos = game.getCardInHand(Player.PEDDERSEN, 2);
        game.playCard(Player.PEDDERSEN, cardDos, 0);

        game.endTurn();
        game.endTurn(); // Peddersens second turn

        //Then Status.OK is returned
        assertThat(game.attackHero(Player.PEDDERSEN, cardDos), is(Status.OK));
    }

    @Test
    public void shouldHaveUnoDeadAndDosHealth1WhenFindusUnoAttacksPeddersensDos(){
        //Given game initialized by beforeEach()

        //When Findus' Uno Minion attacks Peddersens Dos minion
        Card cardUno = game.getCardInHand(Player.FINDUS, 2);
        game.playCard(Player.FINDUS, cardUno, 0);
        game.endTurn();

        Card cardDos = game.getCardInHand(Player.PEDDERSEN, 2);
        game.playCard(Player.PEDDERSEN, cardDos,0);
        game.endTurn();

        game.attackCard(Player.FINDUS, cardUno, cardDos);

        //Then Uno is no longer in field and Dos health is 1
        assertThat(game.getFieldSize(Player.FINDUS), is(0)); //Uno is dead
        assertThat(game.getCardInField(Player.PEDDERSEN, 0).getHealth(), is(1)); //Dos health is 1
    }

    @Test
    public void shouldHaveDosDeadAndTresHealth1WhenPeddersenDosAttacksFindusTres(){
        //Given game initialized by beforeEach()

        //When Peddersens Dos Minion attacks Findus' Tres minion
        Card cardTres = game.getCardInHand(Player.FINDUS, 0);
        game.playCard(Player.FINDUS, cardTres, 0);
        game.endTurn();

        Card cardDos = game.getCardInHand(Player.PEDDERSEN, 2);
        game.playCard(Player.PEDDERSEN, cardDos,0);
        game.endTurn();
        game.endTurn(); //Peddersens Dos is active now
        game.attackCard(Player.PEDDERSEN, cardDos, cardTres);

        //Then Dos is no longer in field and Tres has health 1
        assertThat(game.getFieldSize(Player.PEDDERSEN), is(0)); //Dos is dead
        assertThat(game.getCardInField(Player.FINDUS, 0).getHealth(), is(1)); //Tres health is 1
    }

    @Test
    public void shouldHaveUnoAsInactiveWhenPlayedByFindus(){
        //Given game initialized by beforeEach()

        //When findus plays uno card first turn
        Card cardUno = game.getCardInHand(Player.FINDUS, 2);
        game.playCard(Player.FINDUS, cardUno, 0);

        //Then the uno minion in his field is inactive
        assertThat(game.getCardInField(Player.FINDUS, 0).isActive(), is(false));
    }

    @Test
    public void shouldHaveUnoAsActiveinSecondTurnWhenPlayedByFindusInFirstTurn(){
        //Given game initialized by beforeEach()

        //When findus plays uno first turn and then his second turn starts
        Card cardUno = game.getCardInHand(Player.FINDUS, 2);
        game.playCard(Player.FINDUS, cardUno, 0);
        game.endTurn();
        game.endTurn(); //Findus second turn

        //Then the uno minion is active
        assertThat(game.getCardInField(Player.FINDUS, 0).isActive(), is(true));
    }


    @Test
    public void shouldStillHaveHealth21ForPeddersenWhenFindusAttacksWithUnoFirstTurn(){
        //Given game initialized by beforeEach()

        //When Findus plays uno and tries to attack peddersens hero
        Card cardUno = game.getCardInHand(Player.FINDUS, 2);
        game.playCard(Player.FINDUS, cardUno, 0);
        game.attackHero(Player.FINDUS, cardUno);

        //Then peddersens hero health is still 21
        assertThat(game.getHero(Player.PEDDERSEN).getHealth(), is(21));
    }

    @Test
    public void shouldNotAllowFindusToAttackPeddersensHeroWithUnoFirstTurn(){
        //Given game initialized by beforeEach()

        //When Findus plays uno and tries to attack peddersens hero
        Card cardUno = game.getCardInHand(Player.FINDUS, 2);
        game.playCard(Player.FINDUS, cardUno, 0);

        //Then status ATTACK_NOT_ALLOWED_FOR_NON_ACTIVE_MINION is returned
        assertThat(game.attackHero(Player.FINDUS, cardUno), is(Status.ATTACK_NOT_ALLOWED_FOR_NON_ACTIVE_MINION));
    }

    @Test
    public void shouldStillHave2HealthForFindusDosMinionAfterPeddersenTresMinionAttacksItInSecondTurn(){
        //Given game initialized by beforeEach()

        //When Peddersen attacks Findus minion Dos, with Tres
        Card cardDos = game.getCardInHand(Player.FINDUS, 1);
        game.playCard(Player.FINDUS, cardDos, 0);

        game.endTurn();
        Card cardTres = game.getCardInHand(Player.PEDDERSEN, 1);
        game.playCard(Player.PEDDERSEN, cardTres, 0);
        game.attackCard(Player.PEDDERSEN, cardTres, cardDos);

        //Then Dos still has 2 health, since Tres is inactive
        assertThat(game.getCardInField(Player.FINDUS, 0).getHealth(), is(2));
    }

    @Test
    public void shouldNotAllowPeddersensTresMinionToAttackFindusDosMinionInSecondTurn(){
        //Given game initialized by beforeEach()

        //When Peddersen attacks Findus minion Dos, with Tres
        Card cardDos = game.getCardInHand(Player.FINDUS, 1);
        game.playCard(Player.FINDUS, cardDos, 0);

        game.endTurn();
        Card cardTres = game.getCardInHand(Player.PEDDERSEN, 1);
        game.playCard(Player.PEDDERSEN, cardTres, 0);

        //Then status ATTACK_NOT_ALLOWED_FOR_NON_ACTIVE_MINION is returned since Tres is inactive
        assertThat(game.attackCard(Player.PEDDERSEN, cardTres, cardDos), is(Status.ATTACK_NOT_ALLOWED_FOR_NON_ACTIVE_MINION));
    }

    @Test
    public void shouldReturnStatusOKWhenFindusUnoAttacksPeddersensDosInTurn3(){
        //Given game initialized by beforeEach()

        //When Findus Uno Attacks Peddersens Dos in turn 3
        Card cardUno = game.getCardInHand(Player.FINDUS, 2);
        game.playCard(Player.FINDUS, cardUno,0);
        game.endTurn();

        Card cardDos = game.getCardInHand(Player.PEDDERSEN, 2);
        game.playCard(Player.PEDDERSEN, cardDos,0);
        game.endTurn();

        //Then Status OK is returned
        assertThat(game.attackCard(Player.FINDUS, cardUno, cardDos), is(Status.OK));
    }

    @Test
    public void shouldHaveFindusUnoInactiveAfterItHasAttackedPeddersensHeroInTurn3(){
        //Given game initialized by beforeEach()

        //When Findus Uno Attacks Peddersens Hero in turn 3
        Card cardUno = game.getCardInHand(Player.FINDUS, 2);
        game.playCard(Player.FINDUS, cardUno,0);
        game.endTurn();
        game.endTurn();
        game.attackHero(Player.FINDUS, cardUno);

        //Then Uno is inactive
        assertThat(game.getCardInField(Player.FINDUS, 0).isActive(), is(false));
    }

    @Test
    public void shouldHaveFindusDosInactiveAfterItHasAttackedPeddersensUnoInTurn3(){
        //Given game initialized by beforeEach()

        //When Findus Dos Attacks Peddersens Uno in turn 3
        Card cardDos = game.getCardInHand(Player.FINDUS, 1);
        game.playCard(Player.FINDUS, cardDos,0);
        game.endTurn();

        Card cardUno = game.getCardInHand(Player.PEDDERSEN, 3);
        game.playCard(Player.PEDDERSEN, cardUno,0);
        game.endTurn();
        game.attackCard(Player.FINDUS, cardDos, cardUno);

        //Then Dos is inactive
        assertThat(game.getCardInField(Player.FINDUS, 0).isActive(), is(false));
    }

    @Test
    public void shouldNotAllowFindusToAttackOwnDosMinionUsingUnoMinionInTurnThree(){
        //Given game initialized by beforeEach()

        //When Findus tries to attack own dos minion using uno in turn three
        Card cardUno = game.getCardInHand(Player.FINDUS, 2);
        Card cardDos = game.getCardInHand(Player.FINDUS, 1);
        game.playCard(Player.FINDUS, cardUno, 0);
        game.playCard(Player.FINDUS, cardDos, 0);

        game.endTurn();
        game.endTurn();

        //Then Status.ATTACK_NOT_ALLOWED_ON_OWN_MINION is returned
        assertThat(game.attackCard(Player.FINDUS, cardUno, cardDos), is(Status.ATTACK_NOT_ALLOWED_ON_OWN_MINION));
    }

    @Test
    public void shouldHaveFindusAsCardOwnerForUnoWhenHePlaysUno(){
        //Given game initialized by beforeEach()

        //When findus plays uno
        Card cardUno = game.getCardInHand(Player.FINDUS, 2);

        //Then findus is the owner of uno
        assertThat(cardUno.getOwner(), is(Player.FINDUS));
    }

    @Test
    public void shouldHavePeddersenAsCardOwnerForUnoWhenHePlaysUno(){
        //Given game initialized by beforeEach()

        //When Peddersen plays uno
        Card cardUno = game.getCardInHand(Player.PEDDERSEN, 2);

        //Then Peddersen is the owner of uno
        assertThat(cardUno.getOwner(), is(Player.PEDDERSEN));
    }

    @Test
    public void shouldHaveDeckSizeFourForFindusAtBeginningOfGame(){
        //Given game initialized by beforeEach()

        //When game starts

        //Then Findus deck size is four
        assertThat(game.getDeckSize(Player.FINDUS), is(4));
    }

    @Test
    public void shouldHaveDeckSizeThreeForPeddersenAtHisFirstTurn(){
        //Given game initialized by beforeEach()

        //When Peddersens turn
        game.endTurn();

        //Then Peddersen deck size is four
        assertThat(game.getDeckSize(Player.PEDDERSEN), is(3));
    }

    @Test
    public void shouldHaveFindusAsOwnerForFindusHero(){
        //Given game initialized by beforeEach()

        //When game starts

        //Then Findus is owner of Findus Hero
        assertThat(game.getHero(Player.FINDUS).getOwner(), is(Player.FINDUS));
    }

    @Test
    public void shouldHavePeddersenAsOwnerForPeddersensHero(){
        //Given game initialized by beforeEach()

        //When game starts

        //Then Peddersen is owner of Peddersens Hero
        assertThat(game.getHero(Player.PEDDERSEN).getOwner(), is(Player.PEDDERSEN));
    }

    @Test
    public void shouldNotAllowFindusToAttackPeddersensMinionWhileItIsPeddersensTurn(){
        //Given game initialized by beforeEach()

        //When it is peddersens turn and findus tries to attack peddersens minion Uno using Findus own minion Dos
        Card cardDos = game.getCardInHand(Player.FINDUS, 1);
        game.playCard(Player.FINDUS, cardDos, 0);

        game.endTurn();
        Card cardUno = game.getCardInHand(Player.PEDDERSEN, 3);
        game.playCard(Player.PEDDERSEN, cardUno, 0);

        //Then Status.NOT_PLAYER_IN_TURN is returned as it is still peddersens turn
        assertThat(game.attackCard(Player.FINDUS, cardDos, cardUno), is(Status.NOT_PLAYER_IN_TURN));
    }

    @Test
    public void shouldNotAllowPeddersenToAttackFindusHeroDuringFindusSecondTurn(){
        //Given game initialized by beforeEach()

        //When it is Findus sencond turn and Peddersen tries to attack Findus' hero
        game.endTurn();
        Card cardUno = game.getCardInHand(Player.PEDDERSEN, 3);
        game.playCard(Player.PEDDERSEN, cardUno,0);
        game.endTurn(); //Findus second turn

        //Then Status.NOT_PLAYER_IN_TURN is returned as it is still Findus' turn
        assertThat(game.attackHero(Player.PEDDERSEN, cardUno), is(Status.NOT_PLAYER_IN_TURN));
    }

    @Test
    public void shouldNotAllowPeddersenToUseHisHeroPowerWhenItIsFindusFirstTurn(){
        //Given game initialized by beforeEach()

        //When it is Findus first turn and Peddersen tries to use his own Hero power

        //Then Status.NOT_PLAYER_IN_TURN is returned as it is still Findus' turn
        assertThat(game.usePower(Player.PEDDERSEN), is(Status.NOT_PLAYER_IN_TURN));
    }

    @Test
    public void shouldNotAllowFindusToPlayCardUnoWhenItIsPeddersensTurn(){
        //Given game initialized by beforeEach()

        //When it is Peddersens turn and Findus tries to play card uno
        game.endTurn();
        Card cardUno = game.getCardInHand(Player.FINDUS, 2);

        //Then Status.NOT_PLAYER_IN_TURN is returned as it is still Peddersens turn
        assertThat(game.playCard(Player.FINDUS, cardUno,0), is(Status.NOT_PLAYER_IN_TURN));
    }

    @Test
    public void shouldNotAllowPeddersenToAttackFindusHeroWithFindusOwnUnoCardInTurnFour(){
        //Given game initialized by beforeEach()

        //When peddersen tries to attack findus hero with findus own uno card
        Card cardUno = game.getCardInHand(Player.FINDUS, 2);
        game.playCard(Player.FINDUS, cardUno, 0);

        game.endTurn();
        game.endTurn(); //Findus second turn, where uno card gets active
        game.endTurn();

        //Then Status.NOT_OWNER should be returned
        assertThat(game.attackHero(Player.PEDDERSEN, cardUno), is(Status.NOT_OWNER));
    }

    @Test
    public void shouldNotAllowPeddersenToAttackFindusCardDosUsingFindusCardUnoInTurnFour(){
        //Given game initialized by beforeEach()

        //When peddersen tries to attack findus card dos using findus card uno in turn four
        Card cardUno = game.getCardInHand(Player.FINDUS, 2);
        Card cardDos = game.getCardInHand(Player.FINDUS, 1);
        game.playCard(Player.FINDUS, cardUno, 0);
        game.playCard(Player.FINDUS, cardDos, 0);

        game.endTurn();
        game.endTurn(); //Findus second turn, where cards gets active
        game.endTurn();

        //Then Status.NOT_OWNER should be returned
        assertThat(game.attackCard(Player.PEDDERSEN, cardUno, cardDos), is(Status.NOT_OWNER));
    }

    @Test
    public void shouldNotAllowPeddersenToPlayFindusUnoCardFromFindusHandInTurnTwo(){
        //Given game initialized by beforeEach()

        //When peddersen tries to play findus uno card from findus own hand in turn two
        Card cardUno = game.getCardInHand(Player.FINDUS, 2);
        game.endTurn();

        //Then Status.NOT_OWNER should be returned
        assertThat(game.playCard(Player.PEDDERSEN, cardUno, 0), is(Status.NOT_OWNER));
    }

}
