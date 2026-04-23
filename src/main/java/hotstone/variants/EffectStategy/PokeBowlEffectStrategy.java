package hotstone.variants.EffectStategy;

import hotstone.adapter.EffectWizardAdapter;
import hotstone.framework.Effect;
import hotstone.framework.MutableGame;
import hotstone.framework.Player;
import wizardhub.EffectWizard;

public class PokeBowlEffectStrategy implements Effect {
    EffectWizardAdapter effectWizardAdapter;
    @Override
    public void useEffect(MutableGame game) {
        effectWizardAdapter = new EffectWizardAdapter(game);
        new EffectWizard(effectWizardAdapter).forMe().forHero().doChangeHealthOrRemove(2);
    }
}
