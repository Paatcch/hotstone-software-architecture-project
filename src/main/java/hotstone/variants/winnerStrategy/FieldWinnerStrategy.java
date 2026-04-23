package hotstone.variants.winnerStrategy;

import hotstone.framework.Player;
import hotstone.standard.StandardHotStoneGame;

public class FieldWinnerStrategy implements WinnerStategy{
    @Override
    public Player checkWinner(StandardHotStoneGame game) {
        //Before round four, there will be no winner
        if(game.getTurnNumber() < 7){
            return null;
        }
        //There is no winner if both players have minions in field
        boolean bothHaveMinions = (game.getFieldSize(Player.FINDUS) > 0 && game.getFieldSize(Player.PEDDERSEN) > 0);
        if(bothHaveMinions){
            return null;
        }
        //If Findus have minions and Peddersen does not, then Findus is winner
        if(game.getFieldSize(Player.FINDUS) > 0){
            return Player.FINDUS;
        }
        //If Peddersen have minions and Findus does not, then Peddersen is winner.
        //This also applies if both players field is empty.
        return Player.PEDDERSEN;
    }
}
