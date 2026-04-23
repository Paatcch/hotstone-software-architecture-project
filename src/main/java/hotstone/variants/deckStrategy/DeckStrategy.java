package hotstone.variants.deckStrategy;

import hotstone.framework.MutableCard;
import hotstone.framework.Player;
import hotstone.standard.StandardCard;

import java.util.ArrayList;

public interface DeckStrategy {
    ArrayList<MutableCard> buildDeck(Player who);
}
