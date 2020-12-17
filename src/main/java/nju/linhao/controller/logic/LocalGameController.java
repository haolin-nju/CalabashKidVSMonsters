package main.java.nju.linhao.controller.logic;

import javafx.application.HostServices;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import main.java.nju.linhao.battlefield.BattlefieldController;
import main.java.nju.linhao.controller.window.ClientWindowView;
import main.java.nju.linhao.controller.window.MainWindowView;
import main.java.nju.linhao.creature.Creature;
import main.java.nju.linhao.enums.Formation;
import main.java.nju.linhao.enums.FormationRequest;
import main.java.nju.linhao.enums.LocalGameStatus;
import main.java.nju.linhao.enums.Player;


public class LocalGameController {
    private static LocalGameStatus currStatus;
    private static Scene localClientScene;
    private static Image localIcon;
    private static Player localPlayer;
    private static Creature currCreatureSelected;
    private static HostServices hostServices;
    private static MainWindowView mainWindowView;
    private static ClientWindowView clientWindowView;
    private static BattlefieldController battlefieldController;
    private static Thread threadBattleField;

    public static void init(
            MainWindowView mainWindowViewForInit,
            ClientWindowView clientWindowViewForInit,
            BattlefieldController battlefieldControllerForInit,
            Scene clientScene,
            Image icon,
            HostServices mainHostServices) {
        mainWindowView = mainWindowViewForInit;
        clientWindowView = clientWindowViewForInit;
        battlefieldController = battlefieldControllerForInit;
        localClientScene = clientScene;
        localIcon = icon;
        hostServices = mainHostServices;
        mainWindowView.setHostServices(hostServices);
        currStatus = LocalGameStatus.INIT;
        localPlayer = Player.PLAYER_1;
        threadBattleField = null;
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
                } else {
                    System.err.println("未定义的状态机！原状态：" + currStatus + "目标状态：INIT");
                }
                break;
            case CONNECTING:
                if (currStatus == LocalGameStatus.INIT) {
                    currStatus = LocalGameStatus.CONNECTING;
                } else {
                    System.err.println("未定义的状态机！原状态：" + currStatus + "目标状态：CONNECTING");
                }
                break;
            case READY:
                if (currStatus == LocalGameStatus.CONNECTING) {
                    currStatus = LocalGameStatus.READY;
                } else {
                    System.err.println("未定义的状态机！原状态：" + currStatus + "目标状态：READY");
                }
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
        mainWindowView.logMessages("开始新游戏！");
    }

    public static void resetGame() {
        mainWindowView.logMessages("重置游戏！！");
        currStatus = LocalGameStatus.READY;
    }

    public static void pauseGame() {
        mainWindowView.logMessages("暂停游戏！！");
        currStatus = LocalGameStatus.PAUSE;
    }

    public static void continueGame() {
        mainWindowView.logMessages("继续游戏！！");
        currStatus = LocalGameStatus.RUN;
    }

    public static void endGame() {
        mainWindowView.logMessages("停止游戏！");
        currStatus = LocalGameStatus.END;
    }

    // Battlefield Logic


    // Client Server Logic
    private static void getReady() {
        Stage clientStage = new Stage();
        clientStage.setScene(localClientScene);
        clientStage.setTitle("准备界面");
        clientStage.getIcons().add(localIcon);
        clientStage.setResizable(false);
        clientStage.setOnHidden(event -> {
            if (LocalGameController.getCurrentStatus() == LocalGameStatus.READY) {
                mainWindowView.logMessages("已经准备好了！\n本机IP：" + NetworkController.getLocalIp());
                if (localPlayer == Player.PLAYER_1) {
                    mainWindowView.logMessages("本机阵营：人类阵营");
                } else {
                    mainWindowView.logMessages("本机阵营：妖怪阵营");
                }
                mainWindowView.logMessages("您还可以按'⬅''➡'键切换阵型！");
            } else {
                // TODO
            }
            battlefieldController.repaint();
        });
        clientStage.show();
    }

    // Requests
    public static void requestSetLocalPlayer(Player player) {
        localPlayer = player;
        battlefieldController.setLocalPlayer(player);
    }


    // Requests following
    public static void requestLogMessages(String log) {
        mainWindowView.logMessages(log);
    }

    public static void requestSetFormation(FormationRequest formationRequest) {
        int curFormationIdx = battlefieldController.getFormationIdx();
        Formation[] formations = Formation.values();
        switch (formationRequest) {
            case BACKWARD:
                battlefieldController.setFormation(formations[(curFormationIdx + formations.length - 1) % formations.length]);
                break;
            case FORWARD:
                battlefieldController.setFormation(formations[(curFormationIdx + 1) % formations.length]);
                break;
            case LONG_SNAKE_FORMATION_REQUEST:
                battlefieldController.setFormation(Formation.LONG_SNAKE_FORMATION);
                break;
            case FRONTAL_VECTOR_FORMATION_REQUEST:
                battlefieldController.setFormation(Formation.FRONTAL_VECTOR_FORMATION);
                break;
            case SQUARE_FORMATION_REQUEST:
                battlefieldController.setFormation(Formation.SQUARE_FORMATION);
                break;
            default:
                assert (false);
        }
        battlefieldController.repaint();
        mainWindowView.logMessages(localPlayer + "更换阵型为：" + battlefieldController.getFormation().toString());
    }

    public static void requestRepaint(){
        battlefieldController.repaint();
    }

    public static boolean requestMouseClick(double clickPosX, double clickPosY) {
        // TODO: Mouse click event handle, including select a creature or attack. You can use Battlefirld.getCreatureFromPos() and utilize 泛型
        return true;
    }
}
