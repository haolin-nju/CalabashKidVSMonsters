package main.java.nju.linhao.utils;

import main.java.nju.linhao.battlefield.Battlefield;
import main.java.nju.linhao.battlefield.BattlefieldController;
import main.java.nju.linhao.bullet.Bullet;
import main.java.nju.linhao.bullet.BulletManager;
import main.java.nju.linhao.controller.logic.LocalGameController;
import main.java.nju.linhao.creature.Human;
import main.java.nju.linhao.creature.Monster;
import main.java.nju.linhao.enums.Formation;
import main.java.nju.linhao.enums.LocalGameStatus;
import main.java.nju.linhao.enums.LogType;
import main.java.nju.linhao.enums.Player;
import main.java.nju.linhao.team.HumanTeam;
import main.java.nju.linhao.team.MonsterTeam;
import main.java.nju.linhao.team.TeamBuilder;
import main.java.nju.linhao.io.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class Displayer implements Runnable{
    private LinkedList<Log> logs;
    private HumanTeam humanTeam;
    private MonsterTeam monsterTeam;
    private ArrayList<Human> humans;
    private ArrayList<Monster> monsters;

    private LocalGameStatus localGameStatus;

    private boolean isFirstBullet = true;

    private BattlefieldController battlefieldController;

    private static Displayer displayer = new Displayer();

    private Displayer() {
    }

    public static Displayer getInstance() {
        return displayer;
    }

    public void init(BattlefieldController battlefieldController, LinkedList<Log> logLinkedList){
        this.logs = logLinkedList;
        humanTeam = null;
        monsterTeam = null;
        localGameStatus = LocalGameStatus.INIT;

        this.battlefieldController = battlefieldController;
    }

    public BattlefieldController getBattlefieldController(){
        return battlefieldController;
    }

    private void parser(Log log){
        Player player = log.getPlayer();
        LogType logType = log.getLogType();
        Object logContent = log.getLogContent();
        System.out.println(log);
        switch(logType){
            case TEAM_CREATE:
                if(player == Player.PLAYER_1){
                    humanTeam = battlefieldController.getBattlefield().getHumanTeam();
                    humanTeam.setFormation((Formation) log.getLogContent());
                    humans = humanTeam.getTeamMembers();
                } else if(player == Player.PLAYER_2){
                    monsterTeam = battlefieldController.getBattlefield().getMonsterTeam();
                    monsterTeam.setFormation((Formation) log.getLogContent());
                    monsters = monsterTeam.getTeamMembers();
                }
                if(humanTeam != null && monsterTeam != null){
                    stateChange(LocalGameStatus.RUN);
                }
                battlefieldController.repaint(localGameStatus);
                break;
            case CREATURE_MOVE:
                // 需要：名字和位置
                String creatureMoveInfo = (String) logContent;
                String[] nameAndPosArr = creatureMoveInfo.split(":");
                String curCreatureName = nameAndPosArr[0];
                String[] posStrArr = nameAndPosArr[1].split(",");
                int posX = Integer.parseInt(posStrArr[0]);
                int posY = Integer.parseInt(posStrArr[1]);
                if (player == Player.PLAYER_1) {
                   Iterator<Human> humanIterator = humans.iterator();
                   while(humanIterator.hasNext()){
                       Human curHuman = humanIterator.next();
                       if(curHuman.getCreatureName().equals(curCreatureName)){
                           curHuman.setPos(posX, posY);
                           break;
                       }
                   }
                } else if(player == Player.PLAYER_2) {
                    Iterator<Monster> monsterIterator = monsters.iterator();
                    while(monsterIterator.hasNext()){
                        Monster curMonster = monsterIterator.next();
                        if(curMonster.getCreatureName().equals(curCreatureName)){
                            curMonster.setPos(posX, posY);
                            break;
                        }
                    }
                }
                battlefieldController.getBattlefield().updateCreatureGrids();
                battlefieldController.repaint(localGameStatus);
                break;
            case BULLET_CREATE:
                if(isFirstBullet == true){
                    battlefieldController.getBattlefield().startGlobalBulletDisplayerThreads(localGameStatus);
                    isFirstBullet = false;
                }
                battlefieldController.getBattlefield().addBulletToDisplayer((Bullet) logContent);
                break;
            default:
        }
    }

    private boolean stateChange(LocalGameStatus targetState){//状态机
        switch (targetState){
            case RUN:
                if(localGameStatus == LocalGameStatus.INIT) {
                    localGameStatus = targetState;
                    return true;
                } else {
                    System.err.println("状态转换出错！原状态：" + localGameStatus + "目标状态：RUN");
                    assert(false);
                }
        }
        return false;
    }

    @Override
    public void run() {
        int cnt = 0;
        Iterator<Log> iterator = logs.iterator();
        while(iterator.hasNext()){
            System.out.println("Parsing: " + cnt + " log");
            Log log = iterator.next();
            long sleepTime = log.getTimeMilliseconds();
            try{
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            parser(log);
            cnt++;
        }
    }
}
