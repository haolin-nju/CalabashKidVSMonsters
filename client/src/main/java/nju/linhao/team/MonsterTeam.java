package main.java.nju.linhao.team;

import jdk.nashorn.internal.runtime.arrays.ArrayLikeIterator;
import main.java.nju.linhao.creature.*;
import main.java.nju.linhao.enums.Formation;
import main.java.nju.linhao.utils.Configuration;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class MonsterTeam<T extends Monster> extends Team<Monster> {
    private SnakeEssence snakeEssence;
    private ScorpionEssence scorpionEssence;
    private CentipedeEssence centipedeEssence;
    private ArrayList<Minion> minions;

    public MonsterTeam(SnakeEssence snakeEssence,
                       ScorpionEssence scorpionEssence,
                       CentipedeEssence centipedeEssence,
                       ArrayList<Minion> minions) {
        this.snakeEssence = snakeEssence;
        this.scorpionEssence = scorpionEssence;
        this.centipedeEssence = centipedeEssence;
        this.minions = minions;
        teamMembers.add(snakeEssence);
        teamMembers.add(scorpionEssence);
        teamMembers.add(centipedeEssence);
        teamMembers.addAll(minions);

        // default formation
        this.setFormation(Formation.LONG_SNAKE_FORMATION);
    }

    @Override
    public int setFormation(Formation formation) {
        this.formation = formation;
        int[] rows;
        int[] cols;
        switch (formation) {
            case LONG_SNAKE_FORMATION:
                this.snakeEssence.setPos(7, 19);
                this.scorpionEssence.setPos(6, 19);
                this.centipedeEssence.setPos(8, 19);
                rows = new int[]{5, 9, 4, 10, 3, 11};
                cols = new int[]{19, 19, 19, 19, 19, 19};
                for (int i = 0; i < Configuration.DEFAULT_MINION_NUMS; ++i) {
                    this.minions.get(i).setPos(rows[i], cols[i]);
                }
                return 0;
            case FRONTAL_VECTOR_FORMATION:
                this.snakeEssence.setPos(7, 15);
                this.scorpionEssence.setPos(6, 16);
                this.centipedeEssence.setPos(8, 16);
                rows = new int[]{5, 9, 4, 10, 3, 11};
                cols = new int[]{17, 17, 18, 18, 19, 19};
                for (int i = 0; i < Configuration.DEFAULT_MINION_NUMS; ++i) {
                    this.minions.get(i).setPos(rows[i], cols[i]);
                }
                return 1;
            case SQUARE_FORMATION:
                this.snakeEssence.setPos(7, 16);
                this.scorpionEssence.setPos(6, 16);
                this.centipedeEssence.setPos(8, 16);
                rows = new int[]{5, 8, 5, 8, 5, 8};
                cols = new int[]{16, 17, 17, 18, 18, 19};
                for (int i = 0; i < Configuration.DEFAULT_MINION_NUMS; ++i) {
                    this.minions.get(i).setPos(rows[i], cols[i]);
                }
                return 2;
            default:
                return -1;
        }
    }

    public ArrayList<Monster> getTeamMembers() {
        return teamMembers;
    }

    public SnakeEssence getSnakeEssence() {
        return snakeEssence;
    }

    public ScorpionEssence getScorpionEssence() {
        return scorpionEssence;
    }

    public CentipedeEssence getCentipedeEssence() {
        return centipedeEssence;
    }

    public ArrayList<Minion> getMinions() {
        return minions;
    }
}
