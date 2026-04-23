package hotstone.variants.MinionDecisionStrategy;

import java.util.Random;

public class RandomMinionDecisionStrategy implements MinionDecisionStrategy{
    @Override
    public int getIndexNumber(int fieldSize) {
        Random random = new Random();
        return random.nextInt(fieldSize);
    }
}
