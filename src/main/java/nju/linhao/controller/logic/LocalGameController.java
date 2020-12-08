package main.java.nju.linhao.controller.logic;

import com.sun.security.ntlm.Client;
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
import main.java.nju.linhao.controller.window.ClientWindowController;
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
    private static Player localPlayer;
    private static Creature currCreatureSelected;
    private static HostServices hostServices;
    private static MainWindowController mainWindowController;
    private static ClientWindowController clientWindowController;
    private static BattlefieldController battlefieldController;

    public static void init(
            MainWindowController mainWindowControllerForInit,
            ClientWindowController clientWindowControllerForInit,
            BattlefieldController battlefieldControllerForInit,
            Scene clientScene,
            Image icon,
            HostServices mainHostServices) {
        mainWindowController = mainWindowControllerForInit;
        clientWindowController = clientWindowControllerForInit;
        battlefieldController = battlefieldControllerForInit;
        localClientScene = clientScene;
        localIcon = icon;
        hostServices = mainHostServices;
        mainWindowController.setHostServices(hostServices);
    }

    public static LocalGameStatus getCurrentStatus() {
        return currStatus;
    }

    public static void setCurrentStatus(LocalGameStatus status) {
        // state machine
        switch (status) {
            case INIT:
                if (currStatus == LocalGameStatus.END || currStatus == LocalGameStatus.CONNECTING) {
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
        mainWindowController.logMessages("开始新游戏！");
        getReady();
    }

    public static void resetGame() {
        mainWindowController.logMessages("重置游戏！！");
        currStatus = LocalGameStatus.READY;
    }

    public static void pauseGame() {
        mainWindowController.logMessages("暂停游戏！！");
        currStatus = LocalGameStatus.PAUSE;
    }

    public static void continueGame() {
        mainWindowController.logMessages("继续游戏！！");
        currStatus = LocalGameStatus.RUN;
    }

    public static void endGame() {
        mainWindowController.logMessages("停止游戏！");
        currStatus = LocalGameStatus.END;
    }

    // Battlefield Logic
    public static void changeFormation(Formation formation, Player player) {
        // default player = PLAYER_1
        battlefieldController.setFormation(formation, player);
    }

    public static void requestLogMessages(String log) {
        mainWindowController.logMessages(log);
    }


    // Client Server Logic
    private static void getReady() {
        Stage clientStage = new Stage();
        clientStage.setScene(localClientScene);
        clientStage.setTitle("准备界面");
        clientStage.getIcons().add(localIcon);
        clientStage.setResizable(false);
        clientStage.setOnHidden(event -> {
            if (LocalGameController.getCurrentStatus() == LocalGameStatus.READY) {
                mainWindowController.logMessages("已经准备好了！");
                mainWindowController.logMessages("本机IP：" + NetworkController.getLocalIp());
                if (localPlayer == Player.PLAYER_1) {
                    mainWindowController.logMessages("本机阵营：人类阵营");
                } else {
                    mainWindowController.logMessages("本机阵营：妖怪阵营");
                }
            } else {
                // TODO
            }
        });
        clientStage.show();
    }

    public static void setLocalPlayer(Player player) {
        localPlayer = player;
    }
}
