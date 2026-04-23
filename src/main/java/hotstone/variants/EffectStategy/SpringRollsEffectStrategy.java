package hotstone.variants.EffectStategy;

import hotstone.adapter.EffectWizardAdapter;
import hotstone.framework.Effect;
import hotstone.framework.MutableCard;
import hotstone.framework.MutableGame;
import hotstone.framework.Player;
import hotstone.variants.MinionDecisionStrategy.MinionDecisionStrategy;
import hotstone.variants.MinionDecisionStrategy.RandomMinionDecisionStrategy;
import wizardhub.EffectWizard;

public class SpringRollsEffectStrategy implements Effect {
    MinionDecisionStrategy randomMinionDecisionStrategy = new RandomMinionDecisionStrategy();
    EffectWizardAdapter effectWizardAdapter;

    @Override
    public void useEffect(MutableGame game) {
        Player opponent = Player.computeOpponent(game.getPlayerInTurn());
        if(game.getFieldSize(opponent) == 0){
            return;
        }

        int randomIndex = randomMinionDecisionStrategy.getIndexNumber(game.getFieldSize(opponent));
        MutableCard card = (MutableCard) game.getCardInField(game.getPlayerInTurn(), randomIndex);

        effectWizardAdapter = new EffectWizardAdapter(game);
        new EffectWizard(effectWizardAdapter).forOpponent().forMinionAt(randomIndex).doChangeHealthOrRemove(card.getHealth());
    }
}
