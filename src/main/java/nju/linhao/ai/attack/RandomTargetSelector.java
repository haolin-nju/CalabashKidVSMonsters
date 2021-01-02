package main.java.nju.linhao.ai.attack;

import main.java.nju.linhao.creature.Creature;
import main.java.nju.linhao.enums.CreatureStatus;
import main.java.nju.linhao.enums.Player;

import java.util.ArrayList;
import java.util.Random;

public class RandomTargetSelector implements TargetSelector {
    ArrayList<? extends Creature> toAttackCreatures;

    public RandomTargetSelector(ArrayList<? extends Creature> enemy){
        toAttackCreatures = enemy;
    }

    @Override
    public Creature selectAttackTarget() {
        Random rand = new Random();
        if(rand.nextInt(3) == 0) {
            int creaturesNumber = toAttackCreatures.size();
            Creature attackTarget;
            int selectCount = 0;
            do {
                attackTarget = toAttackCreatures.get(rand.nextInt(creaturesNumber));
                selectCount++;
            } while (attackTarget.getCreatureStatus() == CreatureStatus.DEAD && selectCount < 2);
            return attackTarget;
        } else {
            return null;
        }
    }
}
