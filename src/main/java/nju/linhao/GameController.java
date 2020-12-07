package main.java.nju.linhao;

import main.java.nju.linhao.enums.GameStatus;

public class GameController {
    public static GameStatus getCurrentStatus() {
        return currStatus;
    }

    public static void startNewGame(){
        System.out.println("开始新游戏！");
    }

    public static void resetGame(){
        System.out.println("重置游戏！");
    }

    private static GameStatus currStatus = GameStatus.READY;


}
