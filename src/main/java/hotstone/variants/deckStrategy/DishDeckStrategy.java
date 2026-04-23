package hotstone.variants.deckStrategy;

import hotstone.framework.Effect;
import hotstone.framework.MutableCard;
import hotstone.framework.Player;
import hotstone.standard.GameConstants;
import hotstone.standard.StandardCard;
import hotstone.variants.EffectStategy.NoEffectStrategy;

import java.util.ArrayList;
import java.util.Collections;

public class DishDeckStrategy implements DeckStrategy{
    ArrayList<MutableCard> deckList;
    ArrayList<MutableCard> returnDeckList;
    Effect noEffectStrategy;

    @Override
    public ArrayList<MutableCard> buildDeck(Player who) {
        deckList = new ArrayList<>();
        returnDeckList = new ArrayList<>();
        noEffectStrategy = new NoEffectStrategy();

        for(int i = 0; i < 2; i++){ //adds two of each card
            deckList.add(new StandardCard(GameConstants.BROWN_RICE_CARD,1,1,2, who, noEffectStrategy, ""));
            deckList.add(new StandardCard(GameConstants.FRENCH_FRIES_CARD,1,2,1,who, noEffectStrategy, ""));
            deckList.add(new StandardCard(GameConstants.GREEN_SALAD_CARD, 2,2,3,who, noEffectStrategy, ""));
            deckList.add(new StandardCard(GameConstants.TOMATO_SALAD_CARD,2,3,2,who, noEffectStrategy, ""));
            deckList.add(new StandardCard(GameConstants.POKE_BOWL_CARD,3,2,4,who, noEffectStrategy, ""));
            deckList.add(new StandardCard(GameConstants.PUMPKIN_SOUP_CARD,4,2,7,who, noEffectStrategy, ""));
            deckList.add(new StandardCard(GameConstants.NOODLE_SOUP_CARD,4,5,3,who, noEffectStrategy, ""));
            deckList.add(new StandardCard(GameConstants.SPRING_ROLLS_CARD, 5,3,7,who, noEffectStrategy, ""));
            deckList.add(new StandardCard(GameConstants.BAKED_SALMON_CARD,5,8,2,who, noEffectStrategy, ""));
        }
        //shuffle the list
        Collections.shuffle(deckList);

        //Gets the first three cards, which needs to succeed a mana value condition
        addFirstCardWithManaValueCondition(1);
        addFirstCardWithManaValueCondition(2);
        addFirstCardWithManaValueCondition(4);

        //Shuffles the deck again, since we have just sorted it (a "bad" sorting, but sorting nonetheless)
        Collections.shuffle(deckList);

        //Add the rest of the cards to the deck to be returned
        returnDeckList.addAll(deckList);

        return returnDeckList;
    }

    //Takes the first card in the deck that succeeds the condition of being less than or equal to manaValue
    private void addFirstCardWithManaValueCondition(int manaValue){
        for(MutableCard card : deckList){
            if(card.getManaCost() <= manaValue){
                returnDeckList.add(card);
                deckList.remove(card); //removes, so it won't be added again
                break;
            }
        }
    }
}
