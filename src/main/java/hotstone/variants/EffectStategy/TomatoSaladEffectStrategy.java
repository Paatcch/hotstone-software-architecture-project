package hotstone.variants.EffectStategy;

import hotstone.adapter.EffectWizardAdapter;
import hotstone.framework.Effect;
import hotstone.framework.MutableCard;
import hotstone.framework.MutableGame;
import hotstone.framework.Player;
import hotstone.variants.MinionDecisionStrategy.MinionDecisionStrategy;
import hotstone.variants.MinionDecisionStrategy.RandomMinionDecisionStrategy;
import wizardhub.EffectWizard;

public class TomatoSaladEffectStrategy implements Effect {
    MinionDecisionStrategy randomMinionDecisionStrategy = new RandomMinionDecisionStrategy();
    EffectWizardAdapter effectWizardAdapter;

    @Override
    public void useEffect(MutableGame game) {
        Player currentPlayer = game.getPlayerInTurn();
        if(game.getFieldSize(currentPlayer) == 0){
            return;
        }
        int randomIndex = randomMinionDecisionStrategy.getIndexNumber(game.getFieldSize(currentPlayer));

        effectWizardAdapter = new EffectWizardAdapter(game);
        new EffectWizard(effectWizardAdapter).forMe().forMinionAt(randomIndex).doChangeAttack(1);
    }
}
