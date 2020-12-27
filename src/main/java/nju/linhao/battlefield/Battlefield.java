package main.java.nju.linhao.battlefield;

import main.java.nju.linhao.bullet.Bullet;
import main.java.nju.linhao.bullet.BulletManager;
import main.java.nju.linhao.bullet.HumanBullet;
import main.java.nju.linhao.creature.Creature;
import main.java.nju.linhao.creature.Human;
import main.java.nju.linhao.creature.Monster;
import main.java.nju.linhao.enums.CreatureEnum;
import main.java.nju.linhao.enums.CreatureStatus;
import main.java.nju.linhao.enums.Formation;
import main.java.nju.linhao.enums.Player;
import main.java.nju.linhao.exception.OutofRangeException;
import main.java.nju.linhao.team.HumanTeam;
import main.java.nju.linhao.team.MonsterTeam;
import main.java.nju.linhao.team.Team;
import main.java.nju.linhao.team.TeamBuilder;
import main.java.nju.linhao.utils.Configuration;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class Battlefield implements Runnable {
    private static int columns;
    private static int rows;
    private static Creature[][] creatureGrids;
    private static HumanTeam humanTeam;
    private static MonsterTeam monsterTeam;
    private static BulletManager bulletManager;

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
        monsterTeam = TeamBuilder.buildMonsterTeam(Configuration.DEFAULT_MINION_NUMS);

        bulletManager = new BulletManager();
    }

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }

    public Creature getCreatureFromClickPos(double posX, double posY) throws OutofRangeException {
        if(posX < 0 || posX >= Configuration.CANVAS_WIDTH || posY < 0 || posY >= Configuration.CANVAS_HEIGHT){
            throw new OutofRangeException("Out of Range!");
        }
        int rowIdx = (int) (posY / Configuration.DEFAULT_GRID_HEIGHT);
        int colIdx = (int) (posX / Configuration.DEFAULT_GRID_WIDTH);
        return creatureGrids[rowIdx][colIdx];
    }

    public Creature getCreatureFromPos(int rowIdx, int colIdx) throws OutofRangeException {
        if(rowIdx < 0 || rowIdx >= Configuration.DEFAULT_GRID_ROWS || colIdx < 0 || colIdx >= Configuration.DEFAULT_GRID_COLUMNS){
            throw new OutofRangeException("Out of Range!");
        }
        return creatureGrids[rowIdx][colIdx];
    }

    public Creature getCreatureFromId(Creature creature, Player curPlayer){
        int creatureId = creature.getCreatureId();
        if(curPlayer == Player.PLAYER_1){
            ArrayList<Monster> monsters = monsterTeam.getTeamMembers();
            for (Monster monster : monsters) {
                if (creatureId == monster.getCreatureId()) {
                    return monster;
                }
            }
        } else if(curPlayer == Player.PLAYER_2) {
            ArrayList<Human> humans = humanTeam.getTeamMembers();
            for (Human human : humans) {
                if (creatureId == human.getCreatureId()) {
                    return human;
                }
            }
        }
        return null;
    }

    public HumanTeam getHumanTeam() {
        return humanTeam;
    }

    public MonsterTeam getMonsterTeam() {
        return monsterTeam;
    }

    public synchronized void updateCreatureGrids() {
        creatureGrids = new Creature[this.rows][this.columns];
        ArrayList<Human> humans = humanTeam.getTeamMembers();
        for (Human human : humans) {
            int[] pos = human.getPos();
            creatureGrids[pos[0]][pos[1]] = human;
        }
        ArrayList<Monster> monsters = monsterTeam.getTeamMembers();
        for (Monster monster : monsters) {
            int[] pos = monster.getPos();
            creatureGrids[pos[0]][pos[1]] = monster;
        }
    }

    public void startLocalCreatureThreads(Player player){
        if(player == Player.PLAYER_1){
            ArrayList<Human> humans = humanTeam.getTeamMembers();
            for(Human human : humans){
                Thread humanThread = new Thread(human);
                humanThread.start();
            }
        } else if(player == Player.PLAYER_2) {
            ArrayList<Monster> monsters = monsterTeam.getTeamMembers();
            for (Monster monster : monsters) {
                Thread monsterThread = new Thread(monster);
                monsterThread.start();
            }
        }
    }

    public void addBullet(Bullet bullet){
        bulletManager.addBullet(bullet);
        Thread bulletThread = new Thread(bullet);
        bulletThread.start();
    }

    public BulletManager getBulletManager(){
        return bulletManager;
    }

    @Override
    public void run() {

    }

}