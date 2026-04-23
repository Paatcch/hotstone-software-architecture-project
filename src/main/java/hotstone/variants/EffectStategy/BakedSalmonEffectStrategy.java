package hotstone.variants.EffectStategy;

import hotstone.adapter.EffectWizardAdapter;
import hotstone.framework.Effect;
import hotstone.framework.MutableCard;
import hotstone.framework.MutableGame;
import hotstone.framework.Player;
import hotstone.variants.MinionDecisionStrategy.MinionDecisionStrategy;
import hotstone.variants.MinionDecisionStrategy.RandomMinionDecisionStrategy;
import wizardhub.EffectWizard;

public class BakedSalmonEffectStrategy implements Effect {
    MinionDecisionStrategy randomMinionDecisionStrategy = new RandomMinionDecisionStrategy();
    EffectWizardAdapter effectWizardAdapter;

    @Override
    public void useEffect(MutableGame game) {
        Player opponent = Player.computeOpponent(game.getPlayerInTurn());
        if(game.getFieldSize(opponent) == 0){
            return;
        }
        int randomIndex = randomMinionDecisionStrategy.getIndexNumber(game.getFieldSize(opponent));

        effectWizardAdapter = new EffectWizardAdapter(game);
        new EffectWizard(effectWizardAdapter).forOpponent().forMinionAt(randomIndex).doChangeAttack(2);
    }
}
