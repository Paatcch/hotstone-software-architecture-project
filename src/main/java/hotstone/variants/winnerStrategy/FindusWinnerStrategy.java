package hotstone.variants.winnerStrategy;

import hotstone.framework.Player;
import hotstone.standard.StandardHotStoneGame;

public class FindusWinnerStrategy implements WinnerStategy {
    @Override
    public Player checkWinner(StandardHotStoneGame game) {
        if(game.getTurnNumber() == 9){
            return Player.FINDUS;
        }
        return null;
    }
}
