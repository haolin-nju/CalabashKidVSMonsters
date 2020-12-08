package main.java.nju.linhao.team;

import main.java.nju.linhao.creature.*;

import java.util.concurrent.CopyOnWriteArrayList;

public class TeamBuilder {
    private TeamBuilder(){}

    public static HumanTeam buildHumanTeam(){
        Grandpa grandpa = CreatureFactory.createGrandpa();
        Pangolin pangolin = CreatureFactory.createPangolin();
        CopyOnWriteArrayList<CalabashKid> calabashKids = CreatureFactory.createCalabashKids();
        return new HumanTeam(
                grandpa,
                pangolin,
                calabashKids);
    }

    public static MonsterTeam buildMonsterTeam(int minionNum){
        SnakeEssence snakeEssence = CreatureFactory.createSnakeEssence();
        ScorpionEssence scorpionEssence = CreatureFactory.createScorpionEssence();
        CentipedeEssence centipedeEssence = CreatureFactory.createCentipedeEssence();
        CopyOnWriteArrayList<Minion> minions = CreatureFactory.createMinions(minionNum);
        return new MonsterTeam(
                snakeEssence,
                scorpionEssence,
                centipedeEssence,
                minions);
    }
}
