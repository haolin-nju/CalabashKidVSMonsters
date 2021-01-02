package main.java.nju.linhao.battlefield;

import main.java.nju.linhao.bullet.Bullet;
import main.java.nju.linhao.bullet.BulletDisplayer;
import main.java.nju.linhao.bullet.BulletManager;
import main.java.nju.linhao.bullet.HumanBullet;
import main.java.nju.linhao.creature.Creature;
import main.java.nju.linhao.creature.Human;
import main.java.nju.linhao.creature.Monster;
import main.java.nju.linhao.enums.*;
import main.java.nju.linhao.exception.OutofRangeException;
import main.java.nju.linhao.team.HumanTeam;
import main.java.nju.linhao.team.MonsterTeam;
import main.java.nju.linhao.team.Team;
import main.java.nju.linhao.team.TeamBuilder;
import main.java.nju.linhao.utils.Configuration;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class Battlefield {
    private static int columns;
    private static int rows;
    private static Creature[][] creatureGrids;
    private static HumanTeam humanTeam;
    private static MonsterTeam monsterTeam;
    private static BulletManager bulletManager;
    private static ArrayList<Thread> localCreatureThreads;
    private static Thread bulletManagerThread;
    private static BulletDisplayer bulletDisplayer;
    private static boolean isFirstBullet = true;

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

        localCreatureThreads = new ArrayList<>();

        bulletManager = new BulletManager();
        bulletDisplayer = new BulletDisplayer();
    }

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }

    public synchronized static Creature getCreatureFromBulletPos(double posX, double posY) throws OutofRangeException { // 已经检查过位置的合法性了！
        if (posX < 0 || posX >= Configuration.CANVAS_WIDTH || posY < 0 || posY >= Configuration.CANVAS_HEIGHT) {
            throw new OutofRangeException("Out of Range!");
        }
        int rowIdx = (int) (posY / Configuration.DEFAULT_GRID_HEIGHT);
        int colIdx = (int) (posX / Configuration.DEFAULT_GRID_WIDTH);
//        System.out.println(rowIdx);
//        System.out.println(colIdx);
        return creatureGrids[rowIdx][colIdx];
    }

    public Creature getCreatureFromClickPos(double posX, double posY) throws OutofRangeException {
        if (posX < 0 || posX >= Configuration.CANVAS_WIDTH || posY < 0 || posY >= Configuration.CANVAS_HEIGHT) {
            throw new OutofRangeException("Out of Range!");
        }
        int rowIdx = (int) (posY / Configuration.DEFAULT_GRID_HEIGHT);
        int colIdx = (int) (posX / Configuration.DEFAULT_GRID_WIDTH);
        return creatureGrids[rowIdx][colIdx];
    }

    public Creature getCreatureFromPos(int rowIdx, int colIdx) throws OutofRangeException {
        if (rowIdx < 0 || rowIdx >= Configuration.DEFAULT_GRID_ROWS || colIdx < 0 || colIdx >= Configuration.DEFAULT_GRID_COLUMNS) {
            throw new OutofRangeException("Out of Range!");
        }
        return creatureGrids[rowIdx][colIdx];
    }

    public Creature getOtherCreatureFromId(Creature creature, Player curPlayer) {
        int creatureId = creature.getCreatureId();
        if (curPlayer == Player.PLAYER_1) {
            ArrayList<Monster> monsters = monsterTeam.getTeamMembers();
            for (Monster monster : monsters) {
                if (creatureId == monster.getCreatureId()) {
                    return monster;
                }
            }
        } else if (curPlayer == Player.PLAYER_2) {
            ArrayList<Human> humans = humanTeam.getTeamMembers();
            for (Human human : humans) {
                if (creatureId == human.getCreatureId()) {
                    return human;
                }
            }
        }
        return null;
    }

    public Creature getLocalCreatureFromId(Creature creature, Player curPlayer) {
        int creatureId = creature.getCreatureId();
        if (curPlayer == Player.PLAYER_1) {
            ArrayList<Human> humans = humanTeam.getTeamMembers();
            for (Human human : humans) {
                if (creatureId == human.getCreatureId()) {
                    return human;
                }
            }
        } else if (curPlayer == Player.PLAYER_2) {
            ArrayList<Monster> monsters = monsterTeam.getTeamMembers();
            for (Monster monster : monsters) {
                if (creatureId == monster.getCreatureId()) {
                    return monster;
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
            if(human.getCreatureStatus() == CreatureStatus.ALIVE) {
                creatureGrids[pos[0]][pos[1]] = human;
            }
        }
        ArrayList<Monster> monsters = monsterTeam.getTeamMembers();
        for (Monster monster : monsters) {
            int[] pos = monster.getPos();
            if (monster.getCreatureStatus() == CreatureStatus.ALIVE) {
                creatureGrids[pos[0]][pos[1]] = monster;
            }
        }
    }

    public ArrayList<Thread> startLocalCreatureThreads(Player player) {
        if(player == Player.PLAYER_1) {
            ArrayList<Human> humans = humanTeam.getTeamMembers();
            for (Human human : humans) {
                Thread humanThread = new Thread(human);
                humanThread.start();
                localCreatureThreads.add(humanThread);
            }
        } else if(player == Player.PLAYER_2) {
            ArrayList<Monster> monsters = monsterTeam.getTeamMembers();
            for (Monster monster : monsters) {
                Thread monsterThread = new Thread(monster);
                monsterThread.start();
                localCreatureThreads.add(monsterThread);
            }
        }
        return localCreatureThreads;
    }

    public Thread startLocalBulletManagerThreads() {
        Thread bulletManagerThread = new Thread(bulletManager);
        bulletManagerThread.start();
        return bulletManagerThread;
    }

    public Thread startGlobalBulletDisplayerThreads(LocalGameStatus localGameStatus){
        bulletDisplayer.setLocalGameStatus(localGameStatus);
        Thread bulletDisplayerThread = new Thread(bulletDisplayer);
        bulletDisplayerThread.start();
        return bulletDisplayerThread;
    }

    public BulletDisplayer getBulletDisplayer(){
        return bulletDisplayer;
    }

    public void addBullet(Bullet bullet) {
        bulletManager.addBullet(bullet);
    }

    public void addBulletToDisplayer(Bullet bullet){
        bulletDisplayer.addBullet(bullet);
    }

    public void removeBulletFromDisplayer(Bullet bullet) {
        bulletDisplayer.removeBulletFromDisplayer(bullet);
    }

    public BulletManager getBulletManager() {
        return bulletManager;
    }

    public void clearCreatureGrids() {
        creatureGrids = null;
    }

    public void destroyAllCreatures() {
        humanTeam = TeamBuilder.buildHumanTeam();
        monsterTeam = TeamBuilder.buildMonsterTeam(Configuration.DEFAULT_MINION_NUMS);
        localCreatureThreads = new ArrayList<>();
    }

    public void destroyAllBullets() {
        bulletManager = new BulletManager();
    }

}