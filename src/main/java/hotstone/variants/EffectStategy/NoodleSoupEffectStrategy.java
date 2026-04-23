package hotstone.variants.EffectStategy;

import hotstone.adapter.EffectWizardAdapter;
import hotstone.framework.Effect;
import hotstone.framework.MutableGame;
import wizardhub.EffectWizard;

public class NoodleSoupEffectStrategy implements Effect {
    EffectWizardAdapter effectWizardAdapter;
    @Override
    public void useEffect(MutableGame game) {
        effectWizardAdapter = new EffectWizardAdapter(game);
        new EffectWizard(effectWizardAdapter).forMe().doDrawCard();
    }
}
