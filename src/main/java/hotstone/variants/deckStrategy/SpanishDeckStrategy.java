package hotstone.variants.deckStrategy;

import hotstone.framework.Effect;
import hotstone.framework.MutableCard;
import hotstone.framework.Player;
import hotstone.standard.GameConstants;
import hotstone.standard.StandardCard;
import hotstone.variants.EffectStategy.NoEffectStrategy;

import java.util.ArrayList;

public class SpanishDeckStrategy implements DeckStrategy{
    Effect noEffectStrategy;

    @Override
    public ArrayList<MutableCard> buildDeck(Player who) {
        ArrayList<MutableCard> deckList = new ArrayList<>();
        noEffectStrategy = new NoEffectStrategy();

        deckList.add(new StandardCard(GameConstants.UNO_CARD, 1, 1, 1, who, noEffectStrategy, ""));
        deckList.add(new StandardCard(GameConstants.DOS_CARD, 2, 2, 2, who, noEffectStrategy, ""));
        deckList.add(new StandardCard(GameConstants.TRES_CARD, 3, 3, 3, who, noEffectStrategy, ""));
        deckList.add(new StandardCard(GameConstants.CUATRO_CARD, 2, 3, 1, who, noEffectStrategy, ""));
        deckList.add(new StandardCard(GameConstants.CINCO_CARD, 3, 5, 1, who, noEffectStrategy, ""));
        deckList.add(new StandardCard(GameConstants.SEIS_CARD, 2, 1, 3, who, noEffectStrategy, ""));
        deckList.add(new StandardCard(GameConstants.SIETE_CARD, 3, 2, 4, who, noEffectStrategy, ""));
        return deckList;
    }
}