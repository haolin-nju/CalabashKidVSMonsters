package main.java.nju.linhao.creature;

import static main.java.nju.linhao.utils.Configuration.DEFAULT_SCORPION_ESSENCE_NAME;

public class ScorpionEssence extends Monster {
    public ScorpionEssence(){
        super(DEFAULT_SCORPION_ESSENCE_NAME);
    }

    public ScorpionEssence(double health,
                   double damage,
                   double defense,
                   double speed){
        super(DEFAULT_SCORPION_ESSENCE_NAME, health, damage, defense, speed);
    }
}
