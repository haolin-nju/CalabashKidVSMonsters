package main.java.nju.linhao.creature;

public class SnakeEssence extends Creature {
    public SnakeEssence(){
        super("蛇精");
    }

    public SnakeEssence(double health,
                           double damage,
                           double defense,
                           double speed){
        super("蛇精", health, damage, defense, speed);
    }
}
