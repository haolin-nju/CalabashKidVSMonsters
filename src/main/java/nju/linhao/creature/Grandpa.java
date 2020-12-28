package main.java.nju.linhao.creature;

import static main.java.nju.linhao.utils.Configuration.DEFAULT_GRANDPA_NAME;

public class Grandpa extends Human {
    public Grandpa(){
        super(DEFAULT_GRANDPA_NAME);
    }

    public Grandpa(double health,
                       double damage,
                       double defense,
                       double speed){
        super(DEFAULT_GRANDPA_NAME, health, damage, defense, speed);
    }
}
