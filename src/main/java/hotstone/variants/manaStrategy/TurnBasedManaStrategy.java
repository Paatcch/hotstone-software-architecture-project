package hotstone.variants.manaStrategy;

import hotstone.framework.MutableGame;
import hotstone.framework.Player;

public class TurnBasedManaStrategy implements ManaStrategy{
    @Override
    public int calculateMana(MutableGame game) {
        int turnNumber = game.getTurnNumber();
        int turnNumberFindus = turnNumber / 2 + 1; //+1 because findus' turn are uneven numbers. His first turn is 1/2 +1 = 1
        int turnNumberPeddersen = turnNumber / 2;

        if(turnNumberFindus > 7){
            return 7; //maximum amount of mana
        }
        if(game.getPlayerInTurn().equals(Player.FINDUS)){
            return turnNumberFindus;
        }
        return turnNumberPeddersen;
    }
}
