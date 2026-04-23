package hotstone.standard;
import hotstone.factories.EpsilonStoneFactory;
import TestStub.factories.EpsilonStoneTestStubFactory;
import hotstone.factories.HotStoneFactory;
import hotstone.framework.Card;
import hotstone.framework.Player;
import org.junit.jupiter.api.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

import hotstone.framework.Game;

public class TestEpsilonStone {
    private Game gameTestStub;
    private Game game;

    private HotStoneFactory epsilonStoneTestStubFactory;
    private HotStoneFactory epsilonStoneFactory;

    /** Fixture for AlphaStone testing. */
    @BeforeEach
    public void setUp() {
        epsilonStoneTestStubFactory = new EpsilonStoneTestStubFactory();
        gameTestStub = new StandardHotStoneGame(epsilonStoneTestStubFactory);

        epsilonStoneFactory = new EpsilonStoneFactory();
        game = new StandardHotStoneGame(epsilonStoneFactory);
    }

    @Test
    public void findusPlaysFrenchChefWithPowerRedwineAndDescriptionDeal2DamageToOpponentMinion(){
        //Given game initialized by BeforeEach method with test stub

        //Then Findus hero is FrenchChef with power "Redwine" with description "Deal 2 damage to opponent minion"
        assertThat(gameTestStub.getHero(Player.FINDUS).getType(), is(GameConstants.FRENCH_CHEF_HERO_TYPE));
        assertThat(gameTestStub.getHero(Player.FINDUS).getEffectDescription(), is("Deal 2 damage to opponent minion"));
    }

    @Test
    public void peddersenPlaysItalianChefWithPowerPastaAndDescriptionAdd2AttackToMinion(){
        //Given game initialized by BeforeEach method with test stub

        //Then Peddersens hero is ItalitanChef with power "Pasta" with description "Add 2 attack to minion"
        assertThat(gameTestStub.getHero(Player.PEDDERSEN).getType(), is(GameConstants.ITALIAN_CHEF_HERO_TYPE));
        assertThat(gameTestStub.getHero(Player.PEDDERSEN).getEffectDescription(), is("Add 2 attack to minion"));
    }

    @Test
    public void peddersensTresMinionHasHealth1WhenFindusUsesHeroPowerInTurn3(){
        //Given game initialized by BeforeEach method with test stub

        //When Peddersen has played Card Tres In Turn 2 and Findus uses hero power in turn 3
        gameTestStub.endTurn();
        Card tres = gameTestStub.getCardInHand(Player.PEDDERSEN,1);
        gameTestStub.playCard(Player.PEDDERSEN, tres, 0);
        gameTestStub.endTurn();
        gameTestStub.usePower(Player.FINDUS);

        //Then tres has health 1
        assertThat(tres.getHealth(), is(1));
    }

    @Test
    public void findusUsePowerShouldAffectARandomMinionInPeddersensFieldIfHeHasMoreThanOneMinion(){
        //Given game initialized by BeforeEach method

        //When Peddersen has Uno and Dos minions in field and Findus uses HeroPower
        int unoHasTakenDamage = 0;
        int dosHasTakenDamage = 0;

        for(int i=0; i<100; i++) {
            game = new StandardHotStoneGame(epsilonStoneFactory);

            game.endTurn();
            Card uno = game.getCardInHand(Player.PEDDERSEN, 3);
            game.playCard(Player.PEDDERSEN, uno, 0);
            Card dos = game.getCardInHand(Player.PEDDERSEN, 2);
            game.playCard(Player.PEDDERSEN, dos, 0);
            game.endTurn();
            game.usePower(Player.FINDUS);

            if(uno.getHealth() == -1){
                unoHasTakenDamage++;
            }
            if(dos.getHealth() == 0){
                dosHasTakenDamage++;
            }
        }

        //Then Findus hero power has affected both Peddersens Uno And Tres in a 100 games
        assertThat(unoHasTakenDamage, greaterThanOrEqualTo(1));
        assertThat(dosHasTakenDamage, greaterThanOrEqualTo(1));
    }

    @Test
    public void shouldHaveAttackPowerThreeForUnoMinionInPeddersensFieldWhenPeddersenUsesHeroPower(){
        //Given game initialized by BeforeEach method with test stub

        //When Peddersen has Uno minion in field and uses hero power in turn two
        gameTestStub.endTurn();
        Card uno = gameTestStub.getCardInHand(Player.PEDDERSEN, 3);
        gameTestStub.playCard(Player.PEDDERSEN, uno, 0);
        gameTestStub.usePower(Player.PEDDERSEN);

        //Then uno has attackPower 3
        assertThat(uno.getAttack(), is(3));
    }

    @Test
    public void shouldHaveOnlyOneMinionBeAffectedByUsePowerByPeddersen(){
        //Given game initialized by BeforeEach method with test stub

        //When Peddersen has Uno and Dos minion in field and uses hero power in turn four
        gameTestStub.endTurn();
        Card uno = gameTestStub.getCardInHand(Player.PEDDERSEN, 3);
        Card dos = gameTestStub.getCardInHand(Player.PEDDERSEN, 2);
        gameTestStub.playCard(Player.PEDDERSEN, uno, 0);
        gameTestStub.playCard(Player.PEDDERSEN, dos, 0);
        gameTestStub.endTurn();
        gameTestStub.endTurn();
        gameTestStub.usePower(Player.PEDDERSEN);

        //Then Dos should have attack Power 4 and Uno still has attack power 1
        assertThat(dos.getAttack(), is(4));
        assertThat(uno.getAttack(), is(1));
    }

    @Test
    public void shouldHaveNoEffectWhenFindusUsesHeroPowerAndNoMinionsIsOnPeddersensField(){
        //Given game initialized by BeforeEach method with test stub

        //When Findus uses hero power without any minions in peddersens field
        gameTestStub.usePower(Player.FINDUS);

        //Then it is activated but has no effect
        assertThat(gameTestStub.getHero(Player.FINDUS).getMana(), is(1));
    }

    @Test
    public void shouldHaveNoEffectWhenPeddersenUsesHeroPowerAndNoMinionsIsOnPeddersensField(){
        //Given game initialized by BeforeEach method with test stub

        //When Peddersen uses hero power without any minions in peddersens field
        gameTestStub.endTurn();
        gameTestStub.usePower(Player.PEDDERSEN);

        //Then it is activated but has no effect
        assertThat(gameTestStub.getHero(Player.PEDDERSEN).getMana(), is(1));
    }


}