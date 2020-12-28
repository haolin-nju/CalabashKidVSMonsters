package main.java.nju.linhao.team;

import main.java.nju.linhao.creature.CalabashKid;
import main.java.nju.linhao.creature.Grandpa;
import main.java.nju.linhao.creature.Human;
import main.java.nju.linhao.creature.Pangolin;
import main.java.nju.linhao.enums.Formation;

import java.util.ArrayList;

public class HumanTeam<T extends Human> extends Team<Human>{
    private Grandpa grandpa;
    private Pangolin pangolin;
    private ArrayList<CalabashKid> calabashKids;

    public HumanTeam(Grandpa grandpa,
                     Pangolin pangolin,
                     ArrayList<CalabashKid> calabashKids) {
        this.grandpa = grandpa;
        this.pangolin = pangolin;
        this.calabashKids = calabashKids;
        teamMembers.add(grandpa);
        teamMembers.add(pangolin);
        teamMembers.addAll(calabashKids);

        // default formation
        this.setFormation(Formation.LONG_SNAKE_FORMATION);
    }

    @Override
    public int setFormation(Formation formation) {
        this.formation = formation;
        int[] rows;
        int[] cols;
        switch(formation){
            case LONG_SNAKE_FORMATION:
                this.grandpa.setPos(7,0);
                this.pangolin.setPos(6,0);
                rows = new int[]{8,5,9,4,10,3,11};
                cols = new int[]{0,0,0,0,0,0,0};
                for(int i=0;i<7;++i){
                    this.calabashKids.get(i).setPos(rows[i],cols[i]);
                }
                return 0;
            case FRONTAL_VECTOR_FORMATION:
                this.grandpa.setPos(7,4);
                this.pangolin.setPos(6,3);
                rows = new int[]{8,5,9,4,10,3,11};
                cols = new int[]{3,2,2,1,1,0,0};
                for(int i=0;i<7;++i){
                    this.calabashKids.get(i).setPos(rows[i],cols[i]);
                }
                return 1;
            case SQUARE_FORMATION:
                this.grandpa.setPos(7,3);
                this.pangolin.setPos(6,3);
                rows = new int[]{8,5,8,5,8,5,8};
                cols = new int[]{3,3,2,2,1,1,0};
                for(int i=0;i<7;++i){
                    this.calabashKids.get(i).setPos(rows[i],cols[i]);
                }
                return 2;
            default:
                return -1;
        }
    }

    public ArrayList<Human> getTeamMembers(){
        return teamMembers;
    }

    public Grandpa getGrandpa(){
        return grandpa;
    }

    public Pangolin getPangolin(){
        return pangolin;
    }

    public ArrayList<CalabashKid> getCalabashKids(){
        return calabashKids;
    }
}
