package hotstone.variants.winnerStrategy;

import hotstone.framework.Player;
import hotstone.standard.StandardHotStoneGame;

public class HeroHealthWinnerStategy implements WinnerStategy {
    @Override
    public Player checkWinner(StandardHotStoneGame game) {
        int findusHealth = game.getHeroHealth(Player.FINDUS);
        int peddersenHealth = game.getHeroHealth(Player.PEDDERSEN);

        if(findusHealth <= 0){
            return Player.PEDDERSEN;
        }
        if(peddersenHealth <= 0){
            return Player.FINDUS;
        }
        return null;
    }
}
