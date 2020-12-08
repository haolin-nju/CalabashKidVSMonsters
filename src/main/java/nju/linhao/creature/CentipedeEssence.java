package main.java.nju.linhao.creature;

public class CentipedeEssence extends Creature {
    public CentipedeEssence(){
        super("蜈蚣精");
    }

    public CentipedeEssence(double health,
                        double damage,
                        double defense,
                        double speed){
        super("蜈蚣精", health, damage, defense, speed);
    }
}
