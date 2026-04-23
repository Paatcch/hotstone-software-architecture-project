package hotstone.variants.heroStrategy;

import hotstone.framework.MutableGame;
import hotstone.framework.Player;
import hotstone.standard.GameConstants;
import hotstone.standard.StandardHotStoneGame;

public class ThaiChefHeroStrategy implements HeroStrategy{
    @Override
    public String getType() {
        return GameConstants.THAI_CHEF_HERO_TYPE;
    }

    @Override
    public String getEffectDescription() {
        return "Deal 2 damage to opponent hero";
    }

    @Override
    public void usePower(MutableGame game) {
        game.changeHeroHealth(Player.computeOpponent(game.getPlayerInTurn()), -2);
    }
}
