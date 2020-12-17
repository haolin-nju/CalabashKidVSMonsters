package main.java.nju.linhao.battlefield;

import main.java.nju.linhao.creature.Creature;
import main.java.nju.linhao.creature.Human;
import main.java.nju.linhao.creature.Monster;
import main.java.nju.linhao.enums.CreatureEnum;
import main.java.nju.linhao.exception.OutofRangeException;
import main.java.nju.linhao.team.HumanTeam;
import main.java.nju.linhao.team.MonsterTeam;
import main.java.nju.linhao.team.TeamBuilder;
import main.java.nju.linhao.utils.Configuration;

import java.util.ArrayList;
import java.util.HashMap;

public class Battlefield implements Runnable {
    private static int columns;
    private static int rows;
    private static Creature[][] creatureGrids;
    private static HumanTeam humanTeam;
    private static MonsterTeam monsterTeam;
    private static ArrayList<Human> humans;
    private static ArrayList<Monster> monsters;

    public Battlefield() {
        this(Configuration.DEFAULT_GRID_COLUMNS, Configuration.DEFAULT_GRID_ROWS, Configuration.DEFAULT_MINION_NUMS);
    }

    public Battlefield(int columns, int rows) {
        this(columns, rows, Configuration.DEFAULT_MINION_NUMS);
    }

    public Battlefield(int columns, int rows, int minionNum) {
        this.columns = columns;
        this.rows = rows;

        creatureGrids = new Creature[this.rows][this.columns];

        humanTeam = TeamBuilder.buildHumanTeam();
        humans = humanTeam.getTeamMembers();

        monsterTeam = TeamBuilder.buildMonsterTeam(minionNum);
        monsters = monsterTeam.getTeamMembers();
    }

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }

    public Creature getCreatureFromPos(double posX, int posY) throws Exception {
        if(posX < 0 || posX >= Configuration.CANVAS_WIDTH || posY < 0 || posY >= Configuration.CANVAS_HEIGHT){
            throw new OutofRangeException("Out of Range!");
        }
        int rowIdx = (int) (posY / Configuration.DEFAULT_GRID_HEIGHT);
        int colIdx = (int) (posX / Configuration.DEFAULT_GRID_WIDTH);
        return creatureGrids[rowIdx][colIdx];
    }

    public HumanTeam getHumanTeam() {
        return humanTeam;
    }

    public MonsterTeam getMonsterTeam() {
        return monsterTeam;
    }

    public void updateCreatureGrids() {
        creatureGrids = new Creature[this.rows][this.columns];
        for (Human human : humans) {
            int[] pos = human.getPos();
            creatureGrids[pos[0]][pos[1]] = human;
        }
        for (Monster monster : monsters) {
            int[] pos = monster.getPos();
            creatureGrids[pos[0]][pos[1]] = monster;
        }
    }

    @Override
    public void run() {

    }
}