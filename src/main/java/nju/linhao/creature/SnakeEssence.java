package main.java.nju.linhao.creature;

import static main.java.nju.linhao.utils.Configuration.DEFAULT_SNAKE_ESSENCE_NAME;

public class SnakeEssence extends Monster {
    public SnakeEssence(){
        super(DEFAULT_SNAKE_ESSENCE_NAME);
    }

    public SnakeEssence(double health,
                           double damage,
                           double defense,
                           double speed){
        super(DEFAULT_SNAKE_ESSENCE_NAME, health, damage, defense, speed);
    }
}
