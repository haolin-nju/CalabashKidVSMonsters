package main.java.nju.linhao.creature;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import static main.java.nju.linhao.utils.Configuration.*;

public class CreatureFactory {
    private CreatureFactory(){}

    // for Human Team apis
    public static Grandpa createGrandpa(){
        return new Grandpa();
    }

    public static ArrayList<CalabashKid> createCalabashKids(){
        ArrayList<CalabashKid> calabashKids = new ArrayList<>();
        calabashKids.add(new CalabashKid(DEFAULT_CALABASH_KID_1_NAME));
        calabashKids.add(new CalabashKid(DEFAULT_CALABASH_KID_2_NAME));
        calabashKids.add(new CalabashKid(DEFAULT_CALABASH_KID_3_NAME));
        calabashKids.add(new CalabashKid(DEFAULT_CALABASH_KID_4_NAME));
        calabashKids.add(new CalabashKid(DEFAULT_CALABASH_KID_5_NAME));
        calabashKids.add(new CalabashKid(DEFAULT_CALABASH_KID_6_NAME));
        calabashKids.add(new CalabashKid(DEFAULT_CALABASH_KID_7_NAME));
        return calabashKids;
    }

    public static Pangolin createPangolin(){
        return new Pangolin();
    }

    // for Monsters Team apis
    public static ArrayList<Minion> createMinions(int minionNum){
        ArrayList<Minion> minions = new ArrayList<>();
        for(int idx = 0; idx < minionNum; ++idx){
            minions.add(new Minion(DEFAULT_MINION_NAME + idx));
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
