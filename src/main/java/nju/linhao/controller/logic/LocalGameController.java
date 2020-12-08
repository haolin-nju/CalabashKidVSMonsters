package main.java.nju.linhao.controller.logic;

import javafx.application.HostServices;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import main.java.nju.linhao.battlefield.Battlefield;
import main.java.nju.linhao.controller.window.BattlefieldController;
import main.java.nju.linhao.controller.window.MainWindowController;
import main.java.nju.linhao.creature.Creature;
import main.java.nju.linhao.enums.Formation;
import main.java.nju.linhao.enums.LocalGameStatus;
import main.java.nju.linhao.enums.Player;

import java.io.IOException;

public class LocalGameController {
    private static LocalGameStatus currStatus = LocalGameStatus.INIT;
    private static Scene localClientScene;
    private static Image localIcon;
    private static Creature currCreatureSelected;
    private static NetworkController networkController;

//    public LocalGameController(BattlefieldController battlefieldController,
//                        MainWindowController mainWindowController,
//                        ClientWindowController clientWindowController,
//                        NetworkController networkController,
//                        Scene clientScene,
//                        Image icon){
//        this.battlefieldController = battlefieldController;
//        this.mainWindowController = mainWindowController;
//        this.clientWindowController = clientWindowController;
//        this.networkController = networkController;
//        this.localClientScene = clientScene;
//        this.localIcon = icon;
//    }
//
    public static void init(Scene clientScene, Image icon){
        localClientScene = clientScene;
        localIcon = icon;
    }

    public static LocalGameStatus getCurrentStatus() {
        return currStatus;
    }

    public static void setCurrentStatus(LocalGameStatus status){
        // state machine
        switch(status){
            case INIT:
                if(currStatus == LocalGameStatus.END || currStatus == LocalGameStatus.CONNECTING){
                    currStatus = LocalGameStatus.INIT;
                }
                break;
            case CONNECTING:
                currStatus = (currStatus == LocalGameStatus.INIT ? LocalGameStatus.CONNECTING : LocalGameStatus.INIT);
                break;
            case READY:
                currStatus = (currStatus == LocalGameStatus.CONNECTING ? LocalGameStatus.READY : LocalGameStatus.INIT);
                break;
            // TODO
            default:
                break;

        }
    }

    // Basic Game Logic
    public static void newGame() {
//        changeFormation(Formation.LONG_SNAKE_FORMATION, Player.PLAYER_1); 我觉得这应该变成网络传输的内容
        getReady();
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
        BattlefieldController.setFormation(formation, player);
    }


    // Client Server Logic
    private static void getReady() {
        Stage clientStage = new Stage();
        clientStage.setScene(localClientScene);
        clientStage.setTitle("准备界面");
        clientStage.getIcons().add(localIcon);
        clientStage.setResizable(false);
        clientStage.setOnHidden(event -> {
            if(LocalGameController.getCurrentStatus() == LocalGameStatus.READY){
//                MainWindowController.logMessages("已经准备好了！");
            }
            else{

            }
        });
        clientStage.show();
    }

}
