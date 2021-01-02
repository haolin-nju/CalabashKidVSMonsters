package main.java.nju.linhao.creature;

import main.java.nju.linhao.enums.CreatureStatus;
import main.java.nju.linhao.enums.Direction;
import main.java.nju.linhao.enums.Player;
import main.java.nju.linhao.enums.SelectionStatus;

public class Human extends Creature{

    public Human(String name) {
        super(name, Player.PLAYER_1);
    }

    public Human(boolean forTest,
                    String name,
                    double health,
                    double damage,
                    double defense,
                    double speed){
        super(forTest, name, health, damage, defense, speed);
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
