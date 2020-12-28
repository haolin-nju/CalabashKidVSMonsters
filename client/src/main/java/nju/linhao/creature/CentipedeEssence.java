package main.java.nju.linhao.creature;

import static main.java.nju.linhao.utils.Configuration.DEFAULT_CENTIPEDE_ESSENCE_NAME;

public class CentipedeEssence extends Monster {
    public CentipedeEssence(){
        super(DEFAULT_CENTIPEDE_ESSENCE_NAME);
    }

    public CentipedeEssence(double health,
                        double damage,
                        double defense,
                        double speed){
        super(DEFAULT_CENTIPEDE_ESSENCE_NAME, health, damage, defense, speed);
    }
}
