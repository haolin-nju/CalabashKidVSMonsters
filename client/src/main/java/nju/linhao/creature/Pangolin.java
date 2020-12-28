package main.java.nju.linhao.creature;

import static main.java.nju.linhao.utils.Configuration.DEFAULT_PANGOLIN_NAME;

public class Pangolin extends Human {
    public Pangolin(){
        super(DEFAULT_PANGOLIN_NAME);
    }

    public Pangolin(double health,
                   double damage,
                   double defense,
                   double speed){
        super(DEFAULT_PANGOLIN_NAME, health, damage, defense, speed);
    }
}
