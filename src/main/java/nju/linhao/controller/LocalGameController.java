package main.java.nju.linhao.controller;

import javafx.application.HostServices;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import main.java.nju.linhao.creature.Creature;
import main.java.nju.linhao.enums.Formation;
import main.java.nju.linhao.enums.LocalGameStatus;
import main.java.nju.linhao.enums.Player;

import java.io.IOException;

public class LocalGameController {
    private static LocalGameStatus currStatus = LocalGameStatus.INIT;
    private static Scene localClientScene;
    private static Image localIcon;
    private static HostServices hostServices;
    private static Creature currCreatureSelected;
    private static BattlefieldController battlefieldController;

    public static void init(HostServices mainHostServices, Scene clientScene, Image icon){
        hostServices = mainHostServices;
        localClientScene = clientScene;
        localIcon = icon;
        MainWindowController.init(mainHostServices);
    }

    public static LocalGameStatus getCurrentStatus() {
        return currStatus;
    }

    // Basic Game Logic
    public static void newGame() {
//        changeFormation(Formation.LONG_SNAKE_FORMATION, Player.PLAYER_1); 我觉得这应该变成网络传输的内容
        getPrepared();
        System.out.println("开始准备！");
    }

    public static void resetGame(){
        currStatus = LocalGameStatus.READY;
        System.out.println("重置游戏！");
    }

    public static void pauseGame() {
        currStatus = LocalGameStatus.PAUSE;
        System.out.println("暂停游戏！");
    }

    public static void continueGame() {
        currStatus = LocalGameStatus.RUN;
        System.out.println("继续游戏！");
    }

    public static void endGame(){
        currStatus = LocalGameStatus.END;
        System.out.println("结束游戏！");
    }

    // Battlefield Logic
    public static void changeFormation(Formation formation, Player player){
        // default player = PLAYER_1
        battlefieldController.setFormation(formation, player);
    }


    // Client Server Logic
    private static void getPrepared() {
        Stage clientStage = new Stage();
        clientStage.setScene(localClientScene);
        clientStage.setTitle("准备界面");
        clientStage.getIcons().add(localIcon);
        clientStage.setResizable(false);
        clientStage.show();
    }

}
