package main.java.nju.linhao.ai.attack;

import main.java.nju.linhao.creature.Creature;
import main.java.nju.linhao.enums.Player;

import java.util.ArrayList;
import java.util.Random;

public class RandomTargetSelector implements TargetSelector {
    @Override
    public Creature selectAttackTarget(ArrayList<Creature> toAttackCreatures) {
        Random rand = new Random();
        int creaturesNumber = toAttackCreatures.size();
        return toAttackCreatures.get(rand.nextInt(creaturesNumber));
    }
}
