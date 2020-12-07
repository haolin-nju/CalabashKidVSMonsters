package main.java.nju.linhao.controller;

import javafx.application.HostServices;
import main.java.nju.linhao.creature.Creature;
import main.java.nju.linhao.creature.Grandpa;
import main.java.nju.linhao.enums.Formation;
import main.java.nju.linhao.enums.GameStatus;
import main.java.nju.linhao.enums.Player;

import java.util.concurrent.ConcurrentHashMap;

public class GameController {
    private static GameStatus currStatus = GameStatus.INIT;
    private static HostServices hostServices;
    private static Creature currCreatureSelected;
    private static BattlefieldController battlefieldController;

    public static void init(HostServices mainHostServices){
        hostServices = mainHostServices;
        WindowController.init(mainHostServices);

        // currCreatureSelected =
        currStatus = GameStatus.READY;
    }

    public static GameStatus getCurrentStatus() {
        return currStatus;
    }

    public static void startNewGame(){
        changeFormation(Formation.LONG_SNAKE_FORMATION, Player.PLAYER_1);
        currStatus = GameStatus.RUN;
        System.out.println("开始新游戏！");
    }

    public static void resetGame(){
        currStatus = GameStatus.READY;
        System.out.println("重置游戏！");
    }

    public static void pauseGame() {
        currStatus = GameStatus.PAUSE;
        System.out.println("暂停游戏！");
    }

    public static void continueGame() {
        currStatus = GameStatus.RUN;
        System.out.println("继续游戏！");
    }

    public static void endGame(){
        currStatus = GameStatus.END;
        System.out.println("结束游戏！");
    }

    public static void changeFormation(Formation formation, Player player){
        // default player = PLAYER_1
        battlefieldController.setFormation(formation, player);
    }

}
