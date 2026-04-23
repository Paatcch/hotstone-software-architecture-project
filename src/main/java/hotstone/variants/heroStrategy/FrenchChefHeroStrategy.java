package hotstone.variants.heroStrategy;

import hotstone.framework.MutableGame;
import hotstone.framework.Player;
import hotstone.standard.GameConstants;
import hotstone.standard.StandardCard;
import hotstone.variants.MinionDecisionStrategy.MinionDecisionStrategy;

public class FrenchChefHeroStrategy implements HeroStrategy {
    MinionDecisionStrategy minionDecisionStrategy;

    public FrenchChefHeroStrategy(MinionDecisionStrategy minionDecisionStrategy){
        this.minionDecisionStrategy = minionDecisionStrategy;
    }

    @Override
    public String getType() {
        return GameConstants.FRENCH_CHEF_HERO_TYPE;
    }

    @Override
    public String getEffectDescription() {
        return "Deal 2 damage to opponent minion";
    }

    @Override
    public void usePower(MutableGame game) {
        Player opponent = Player.computeOpponent(game.getPlayerInTurn());
        int fieldSize = game.getFieldSize(opponent);
        if(fieldSize == 0) {return;}
        int indexNumber = minionDecisionStrategy.getIndexNumber(fieldSize);
        StandardCard minion = (StandardCard) game.getCardInField(opponent, indexNumber);
        minion.takeDamage(2);
    }
}
