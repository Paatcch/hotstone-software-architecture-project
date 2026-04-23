package hotstone.framework;

public interface MutableGame extends Game{
    void changeHeroHealth(Player who, int amount);
    void changeMinionAttackPower(MutableCard card, int amount);
    void drawCard(Player who);
    void removeMinionIfHealthIsBelowOne(MutableCard card);
    void changeMinionHealth(MutableCard card, int amount);
}
