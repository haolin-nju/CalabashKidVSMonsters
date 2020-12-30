package main.java.nju.linhao.creature;

import main.java.nju.linhao.enums.Player;

public class Human extends Creature{

    public Human(String name) {
        super(name, Player.PLAYER_1);
    }

    public Human(String name,
                 double health,
                 double damage,
                 double defense,
                 double speed){
        super(name,
                Player.PLAYER_1,
                health,
                damage,
                defense,
                speed);
    }
}
