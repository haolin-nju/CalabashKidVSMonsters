package main.java.nju.linhao.team;

import main.java.nju.linhao.creature.*;

import java.util.ArrayList;


public class TeamBuilder {
    private TeamBuilder(){}

    public static HumanTeam buildHumanTeam(){
        Grandpa grandpa = CreatureFactory.createGrandpa();
        Pangolin pangolin = CreatureFactory.createPangolin();
        ArrayList<CalabashKid> calabashKids = CreatureFactory.createCalabashKids();
        return new HumanTeam(
                grandpa,
                pangolin,
                calabashKids);
    }

    public static MonsterTeam buildMonsterTeam(int minionNum){
        SnakeEssence snakeEssence = CreatureFactory.createSnakeEssence();
        ScorpionEssence scorpionEssence = CreatureFactory.createScorpionEssence();
        CentipedeEssence centipedeEssence = CreatureFactory.createCentipedeEssence();
        ArrayList<Minion> minions = CreatureFactory.createMinions(minionNum);
        return new MonsterTeam(
                snakeEssence,
                scorpionEssence,
                centipedeEssence,
                minions);
    }
}
