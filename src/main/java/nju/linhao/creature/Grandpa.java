package main.java.nju.linhao.creature;

public class Grandpa extends Human {
    public Grandpa(){
        super("爷爷");
    }

    public Grandpa(double health,
                       double damage,
                       double defense,
                       double speed){
        super("爷爷", health, damage, defense, speed);
    }
}
