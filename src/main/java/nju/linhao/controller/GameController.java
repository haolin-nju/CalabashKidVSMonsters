package main.java.nju.linhao.controller;

import javafx.application.HostServices;
import main.java.nju.linhao.creature.Creature;
import main.java.nju.linhao.creature.Grandpa;
import main.java.nju.linhao.enums.GameStatus;

import java.util.concurrent.ConcurrentHashMap;

public class GameController{
    private static GameStatus currStatus;
    private static HostServices hostServices;
    private static Creature currCreatureSelected;

    public static void init(HostServices mainHostServices){
        currStatus = GameStatus.INIT;
        hostServices = mainHostServices;

        // currCreatureSelected =
        currStatus = GameStatus.READY;
    }

    public static GameStatus getCurrentStatus() {
        return currStatus;
    }

    public static void startNewGame(){
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

}
