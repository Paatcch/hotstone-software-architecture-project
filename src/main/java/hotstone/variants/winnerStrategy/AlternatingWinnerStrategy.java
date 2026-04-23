package hotstone.variants.winnerStrategy;

import hotstone.framework.Player;
import hotstone.standard.StandardHotStoneGame;

public class AlternatingWinnerStrategy implements WinnerStategy{
    private WinnerStategy beforeRound6Strategy;
    private WinnerStategy afterRound6Strategy;

    public AlternatingWinnerStrategy(WinnerStategy beforeRound6Strategy, WinnerStategy afterRound6Strategy){
        this.beforeRound6Strategy = beforeRound6Strategy;
        this.afterRound6Strategy = afterRound6Strategy;
    }

    @Override
    public Player checkWinner(StandardHotStoneGame game) {
        //If the game has lasted 6 rounds or less
        if(game.getTurnNumber() <= 11){
            return beforeRound6Strategy.checkWinner(game);
        } //If the game has lasted more than round 6
        return afterRound6Strategy.checkWinner(game);
    }
}
