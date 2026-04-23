package hotstone.variants.winnerStrategy;
import hotstone.framework.Player;
import hotstone.standard.StandardHotStoneGame;

public interface WinnerStategy {
    Player checkWinner(StandardHotStoneGame game);
}
