package main.java.nju.linhao.team;

import main.java.nju.linhao.creature.Creature;
import main.java.nju.linhao.creature.Human;
import main.java.nju.linhao.enums.Formation;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Team<T extends Creature> implements Serializable {
    protected Formation formation = Formation.LONG_SNAKE_FORMATION;
    protected ArrayList<T> teamMembers = new ArrayList<>();

    public int setFormation(Formation formation) {
        this.formation = formation;
        return -1;
    }

    public Formation getFormation(){
        return formation;
    }

    public ArrayList<T> getTeamMemebers(){
        return teamMembers;
    }
}
