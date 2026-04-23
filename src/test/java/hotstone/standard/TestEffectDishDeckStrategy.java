package hotstone.standard;

import TestStub.GameTestSpy;
import hotstone.framework.Effect;
import hotstone.variants.EffectStategy.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestEffectDishDeckStrategy {

    GameTestSpy game;

    @BeforeEach
    public void setUp() {
        game = new GameTestSpy();
    }

    @Test
    public void brownRiceCallsChangeHeroHealth(){
        //GIVEN the Effect object for Brown Rice
        Effect effect = new BrownRiceEffectStrategy();

        //WHEN the 'useEffect(mutableGame)' method is called
        effect.useEffect(game);

        //THEN the Test Spy has recorded that the method 'changeHeroHealth(who, amount)' has been called with who=PEDDERSEN, and delta=-1.
        assertThat(game.getLastMethodCall(), is("changeHeroHealth PEDDERSEN -1"));
    }

    @Test
    public void tomatoSaladCallsChangeHeroHealth(){
        //GIVEN the Effect object for Tomato Salad
        Effect effect = new TomatoSaladEffectStrategy();

        //WHEN the 'useEffect(mutableGame)' method is called
        effect.useEffect(game);

        //THEN the Test Spy has recorded that the method 'changeMinionAttackPower(card, amount)' has been called with card=BROWN_RICE, and amount 1.
        assertThat(game.getLastMethodCall(), is("changeMinionAttackPower Brown-Rice 1"));
    }

    @Test
    public void tomatoSaladCallsGetFieldSizeWhenOnlyThatCardIsInField(){
        //GIVEN the Effect object for Tomato Salad and no other card is in Findus field
        Effect effect = new TomatoSaladEffectStrategy();
        game.setFieldSizeInt(0); //1 because the tomato salad is in the field

        //WHEN the 'useEffect(mutableGame)' method is called
        effect.useEffect(game);

        //THEN the Test Spy has recorded that the method 'getFieldSize(who)' has been called with who=FINDUS.
        assertThat(game.getLastMethodCall(), is("getFieldSize FINDUS"));
    }

    @Test
    public void pokeBowlRiceCallsChangeHeroHealth(){
        //GIVEN the Effect object for poke bowl
        Effect effect = new PokeBowlEffectStrategy();

        //WHEN the 'useEffect(mutableGame)' method is called
        effect.useEffect(game);

        //THEN the Test Spy has recorded that the method 'changeHeroHealth(who, amount)' has been called with who=PEDDERSEN, and delta=-1.
        assertThat(game.getLastMethodCall(), is("changeHeroHealth FINDUS 2"));
    }

    @Test
    public void noodleSoupCallsDrawCard(){
        //GIVEN the Effect object for noodle soup
        Effect effect = new NoodleSoupEffectStrategy();

        //WHEN the 'useEffect(mutableGame)' method is called
        effect.useEffect(game);

        //THEN the Test Spy has recorded that the method 'drawCard(who)' has been called with who=FINDUS
        assertThat(game.getLastMethodCall(), is("drawCard FINDUS"));
    }

    @Test
    public void springRollsCallsRemoveMinionIfHealthIsBelowOne(){
        //GIVEN the Effect object for noodle soup
        Effect effect = new SpringRollsEffectStrategy();

        //WHEN the 'useEffect(mutableGame)' method is called
        effect.useEffect(game);

        //THEN the Test Spy has recorded that the method 'changeMinionHealth' with parameter Brown-Rice and 1 (the health of brown rice)
        assertThat(game.getAllMethodsCalled().get(0), is("changeMinionHealth Brown-Rice 1"));
    }

    @Test
    public void springRollsCallsGetFieldSizeWhenNoMinionsInOpponentsField(){
        //GIVEN the Effect object for spring rolls and no card is in Peddersens field
        Effect effect = new SpringRollsEffectStrategy();
        game.setFieldSizeInt(0);

        //WHEN Findus calls the 'useEffect(mutableGame)' method
        effect.useEffect(game);

        //THEN the Test Spy has recorded that the method 'getFieldSize(who)' has been called with who=PEDDERSEN.
        assertThat(game.getLastMethodCall(), is("getFieldSize PEDDERSEN"));
    }

    @Test
    public void bakedSalmonCallsChangeMinionAttackPower(){
        //GIVEN the Effect object for Tomato Salad
        Effect effect = new BakedSalmonEffectStrategy();

        //WHEN the 'useEffect(mutableGame)' method is called
        effect.useEffect(game);

        //THEN the Test Spy has recorded that the method 'changeMinionAttackPower(card, amount)' has been called with card=BROWN_RICE, and amount 2.
        assertThat(game.getLastMethodCall(), is("changeMinionAttackPower Brown-Rice 2"));
    }

    @Test
    public void bakedSalmonCallsGetFieldSizeWhenNoMinionsInOpponentsField(){
        //GIVEN the Effect object for baked salmon and no card is in Peddersens field
        Effect effect = new BakedSalmonEffectStrategy();
        game.setFieldSizeInt(0);

        //WHEN Findus calls the 'useEffect(mutableGame)' method
        effect.useEffect(game);

        //THEN the Test Spy has recorded that the method 'getFieldSize(who)' has been called with who=PEDDERSEN.
        assertThat(game.getLastMethodCall(), is("getFieldSize PEDDERSEN"));
    }












}
