package main.java.nju.linhao.creature;

import java.util.concurrent.CopyOnWriteArrayList;

public class CreatureFactory {
    private CreatureFactory(){}

    // for Human Team apis
    public static Grandpa createGrandpa(){
        return new Grandpa();
    }

    public static CopyOnWriteArrayList<CalabashKid> createCalabashKids(){
        CopyOnWriteArrayList<CalabashKid> calabashKids = new CopyOnWriteArrayList<>();
        calabashKids.add(new CalabashKid("大娃"));
        calabashKids.add(new CalabashKid("二娃"));
        calabashKids.add(new CalabashKid("三娃"));
        calabashKids.add(new CalabashKid("四娃"));
        calabashKids.add(new CalabashKid("五娃"));
        calabashKids.add(new CalabashKid("六娃"));
        calabashKids.add(new CalabashKid("七娃"));
        return calabashKids;
    }

    public static Pangolin createPangolin(){
        return new Pangolin();
    }

    // for Monsters Team apis
    public static CopyOnWriteArrayList<Minion> createMinions(int minionNum){
        CopyOnWriteArrayList<Minion> minions = new CopyOnWriteArrayList<>();
        for(int idx = 0; idx < minionNum; ++idx){
            minions.add(new Minion("喽啰" + idx));
        }
        return minions;
    }

    public static SnakeEssence createSnakeEssence(){
        return new SnakeEssence();
    }

    public static ScorpionEssence createScorpionEssence(){
        return new ScorpionEssence();
    }

    public static CentipedeEssence createCentipedeEssence(){
        return new CentipedeEssence();
    }

}
