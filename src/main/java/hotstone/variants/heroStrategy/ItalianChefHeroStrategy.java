package hotstone.variants.heroStrategy;

import hotstone.framework.MutableGame;
import hotstone.framework.Player;
import hotstone.standard.GameConstants;
import hotstone.standard.StandardCard;
import hotstone.variants.MinionDecisionStrategy.MinionDecisionStrategy;

public class ItalianChefHeroStrategy implements HeroStrategy {
    MinionDecisionStrategy minionDecisionStrategy;

    public ItalianChefHeroStrategy(MinionDecisionStrategy minionDecisionStrategy){
        this.minionDecisionStrategy = minionDecisionStrategy;
    }

    @Override
    public String getType() { return GameConstants.ITALIAN_CHEF_HERO_TYPE;}

    @Override
    public String getEffectDescription() {
        return "Add 2 attack to minion";
    }

    @Override
    public void usePower(MutableGame game) {
        int fieldSize = game.getFieldSize(game.getPlayerInTurn());
        if(fieldSize == 0) {return;}
        int indexNumber = minionDecisionStrategy.getIndexNumber(fieldSize);
        StandardCard minion = (StandardCard) game.getCardInField(game.getPlayerInTurn(), indexNumber);
        game.changeMinionAttackPower(minion, 2);
    }
}
