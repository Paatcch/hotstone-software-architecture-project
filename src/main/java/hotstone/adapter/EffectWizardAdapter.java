package hotstone.adapter;

import hotstone.framework.MutableCard;
import hotstone.framework.MutableGame;
import hotstone.framework.Player;
import wizardhub.HotStoneGameTarget;

public class EffectWizardAdapter implements HotStoneGameTarget {
    MutableGame game;

    public EffectWizardAdapter(MutableGame standardHotStoneGame){
        game = standardHotStoneGame;
    }

    @Override
    public int getThePlayerInTurn() {
        if(game.getPlayerInTurn().equals(Player.FINDUS)) {
            return 0;
        }
        return 1;
    }

    @Override
    public int getSizeOfField(int playerIndex) {
        return game.getFieldSize(getPlayerFromIndex(playerIndex));
    }

    @Override
    public void deltaHealthOrRemoveMinion(int playerIndex, int fieldIndex, int healthDelta) {
        Player player = getPlayerFromIndex(playerIndex);
        MutableCard card = (MutableCard) game.getCardInField(player, fieldIndex);
        game.changeMinionHealth(card, healthDelta);
    }

    @Override
    public void deltaAttackMinion(int playerIndex, int fieldIndex, int attackDelta) {
        Player player = getPlayerFromIndex(playerIndex);
        MutableCard card = (MutableCard) game.getCardInField(player, fieldIndex);
        game.changeMinionAttackPower(card, attackDelta);
    }

    @Override
    public void setTauntCategoryMinion(int playerIndex, int fieldIndex, boolean isTaunt) {
        //Irrelevant for our game
    }

    @Override
    public void deltaHealthHero(int playerIndex, int healthDelta) {
        Player player = getPlayerFromIndex(playerIndex);
        game.changeHeroHealth(player, healthDelta);
    }

    @Override
    public void drawCard(int playerIndex) {
        game.drawCard(getPlayerFromIndex(playerIndex));
    }

    public Player getPlayerFromIndex(int index){
        return index == 0 ? Player.FINDUS : Player.PEDDERSEN;
    }
}
