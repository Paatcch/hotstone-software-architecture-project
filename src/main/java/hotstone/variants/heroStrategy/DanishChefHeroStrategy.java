package hotstone.variants.heroStrategy;

import hotstone.framework.Card;
import hotstone.framework.Effect;
import hotstone.framework.MutableGame;
import hotstone.framework.Player;
import hotstone.standard.GameConstants;
import hotstone.standard.StandardCard;
import hotstone.standard.StandardHotStoneGame;
import hotstone.variants.EffectStategy.NoEffectStrategy;

public class DanishChefHeroStrategy implements HeroStrategy{
    Effect noEffectStrategy = new NoEffectStrategy();

    @Override
    public String getType() {
        return GameConstants.DANISH_CHEF_HERO_TYPE;
    }

    @Override
    public String getEffectDescription() {
        return "Summon Sovs card";
    }

    @Override
    public void usePower(MutableGame game) {
        Card sovs = new StandardCard(GameConstants.SOVS_CARD, 0,1,1, game.getPlayerInTurn(), noEffectStrategy, "");
        game.playCard(game.getPlayerInTurn(), sovs, 0);
    }
}
