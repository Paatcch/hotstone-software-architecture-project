package hotstone.framework;

public interface MutableCard extends Card{
    void takeDamage(int damage);
    void setActive(boolean verdict);
    void changeAttackPower(int amount);
    Effect getEffect();
}
