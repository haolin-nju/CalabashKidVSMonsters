package main.java.nju.linhao.creature;

public class ScorpionEssence extends Creature {
    public ScorpionEssence(){
        super("蝎子精");
    }

    public ScorpionEssence(double health,
                   double damage,
                   double defense,
                   double speed){
        super("蝎子精", health, damage, defense, speed);
    }
}
