package main.java.nju.linhao.controller.logic;

import javafx.application.HostServices;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import main.java.nju.linhao.battlefield.Battlefield;
import main.java.nju.linhao.battlefield.BattlefieldController;
import main.java.nju.linhao.controller.window.ClientWindowView;
import main.java.nju.linhao.controller.window.MainWindowView;
import main.java.nju.linhao.creature.Creature;
import main.java.nju.linhao.enums.*;
import main.java.nju.linhao.exception.OutofRangeException;
import main.java.nju.linhao.team.HumanTeam;
import main.java.nju.linhao.team.MonsterTeam;
import main.java.nju.linhao.team.Team;

import java.io.IOException;


public class LocalGameController {
    private LocalGameStatus currStatus;
    private Scene localClientScene;
    private Image localIcon;
    private Player localPlayer;
    private Creature currCreatureSelected;
    private HostServices hostServices;
    private MainWindowView mainWindowView;
    private ClientWindowView clientWindowView;
    private BattlefieldController battlefieldController;
    private NetworkController networkController;
    private Thread threadBattleField;

    private static LocalGameController localGameController = new LocalGameController();
    private LocalGameController(){}
    public static LocalGameController getInstance(){
        return localGameController;
    }

    public void init(
            MainWindowView mainWindowViewForInit,
            ClientWindowView clientWindowViewForInit,
            BattlefieldController battlefieldControllerForInit,
            NetworkController networkControllerForInit,
            Scene clientScene,
            Image icon,
            HostServices mainHostServices) {
        mainWindowView = mainWindowViewForInit;
        clientWindowView = clientWindowViewForInit;
        battlefieldController = battlefieldControllerForInit;
        networkController = networkControllerForInit;
        localClientScene = clientScene;
        localIcon = icon;
        hostServices = mainHostServices;
        mainWindowView.setHostServices(hostServices);
        currStatus = LocalGameStatus.INIT;
        localPlayer = Player.PLAYER_1;
        threadBattleField = null;
    }

    public LocalGameStatus getCurrentStatus() {
        return currStatus;
    }

    public void setCurrentStatus(LocalGameStatus status) {
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
            case RUN:
                if(currStatus == LocalGameStatus.READY){
                    currStatus = LocalGameStatus.RUN;
                } else {
                    System.err.println("未定义的状态机！原状态：" + currStatus + "目标状态：RUN");
                }
                // TODO: add more state machine
            default:
                break;

        }
    }

    // Basic Game Logic
    public void newGame() {
//        changeFormation(Formation.LONG_SNAKE_FORMATION, Player.PLAYER_1); 我觉得这应该变成网络传输的内容
        getReady();
        mainWindowView.logMessages("开始新游戏！");
    }

    public void resetGame() {
        mainWindowView.logMessages("重置游戏！！");
        currStatus = LocalGameStatus.READY;
    }

    public void pauseGame() {
        mainWindowView.logMessages("暂停游戏！！");
        currStatus = LocalGameStatus.PAUSE;
    }

    public void continueGame() {
        mainWindowView.logMessages("继续游戏！！");
        currStatus = LocalGameStatus.RUN;
    }

    public void endGame() {
        mainWindowView.logMessages("停止游戏！");
        currStatus = LocalGameStatus.END;
    }

    // Battlefield Logic


    // Client Server Logic
    private void getReady() {
        Stage clientStage = new Stage();
        clientStage.setScene(localClientScene);
        clientStage.setTitle("准备界面");
        clientStage.getIcons().add(localIcon);
        clientStage.setResizable(false);
        clientStage.setOnHidden(event -> {
            if (localPlayer == Player.PLAYER_1) {
                mainWindowView.logMessages("本机阵营：人类阵营");
            } else {
                mainWindowView.logMessages("本机阵营：妖怪阵营");
            }
            mainWindowView.logMessages("您还可以按'⬅''➡'键切换阵型！");
            battlefieldController.repaint();
            battlefieldController.setDefaultSelectedCreature();
        });
        clientStage.show();
    }

    public void requestNetworkController(MessageType messageType, String destIp){
        networkController.setDestIp(destIp);
        Thread networkThread = new Thread(networkController);
        networkThread.start();
    }

    public void requestGameStart(){
        LocalGameController.getInstance().setCurrentStatus(LocalGameStatus.READY);
    }

    public String getLocalIp(){
        return networkController.getLocalIp();
    }

    public Player getLocalPlayer(){
        return localPlayer;
    }

    public BattlefieldController getBattlefieldController(){
        return battlefieldController;
    }


    // Requests
    public void requestSetLocalPlayer(Player player) {
        localPlayer = player;
        battlefieldController.setLocalPlayer(player);
    }


    // Requests following
    public void requestLogMessages(String log) {
        mainWindowView.logMessages(log);
    }

    public void requestSetFormation(FormationRequest formationRequest) {
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

    public void requestClearInfo(){
        battlefieldController.clear();
    }

    public void requestRepaint(){
        battlefieldController.repaint();
    }

    public void requestMouseClick(double clickPosX, double clickPosY) throws OutofRangeException {
        // TODO: Mouse click event handle, including select a creature or attack. You can use Battlefirld.getCreatureFromPos() and utilize 泛型
        battlefieldController.requestMouseClick(clickPosX, clickPosY, localPlayer);
    }

    public Formation requestGetTeamFormation(){
        if(localPlayer == Player.PLAYER_1){
            return battlefieldController.getBattlefield().getHumanTeam().getFormation();
        } else if(localPlayer == Player.PLAYER_2){
            return battlefieldController.getBattlefield().getMonsterTeam().getFormation();
        } else{
            System.err.println("意外的本地玩家！");
            return null;
        }
    }

    public void requestSetTeamFormation(Formation formation){
        // 为对方在本机设置阵型
        if(localPlayer == Player.PLAYER_1){
            battlefieldController.getBattlefield().getMonsterTeam().setFormation(formation);
        } else if(localPlayer == Player.PLAYER_2){
            battlefieldController.getBattlefield().getHumanTeam().setFormation(formation);
        } else{
            System.err.println("意料之外的阵营！");
        }
        battlefieldController.repaint();
    }

    public void requestStartCreatureThreads(){
        battlefieldController.getBattlefield().startLocalCreatureThreads(localPlayer);
    }

    public void requestCreatureMove(Direction direction){
        Creature curSelectedCreature = battlefieldController.letCurSelectedCreatureMove(direction);
        battlefieldController.repaint();
        try {
            networkController.sendMessage(MessageType.CREATURE_MOVE, curSelectedCreature);
        } catch(IOException e){
            requestLogMessages("当前移动没有被另一端接收！");
        }
    }

    public synchronized void requestCreatureMove(Creature creature, Direction direction){
            battlefieldController.letCreatureMove(creature, direction);
            battlefieldController.repaint();
            try {
                networkController.sendMessage(MessageType.CREATURE_MOVE, creature);
            } catch (IOException e) {
                requestLogMessages("当前移动没有被另一端接收！");
            }
    }

    public void requestOthersCreatureMove(Creature creature) {
        Battlefield battlefield = battlefieldController.getBattlefield();
        synchronized (battlefield) {
            Creature creatureToMove = battlefield.getCreatureFromId(creature, localPlayer);
            int[] newPos = creature.getPos();
            creatureToMove.setPos(newPos[0], newPos[1]);
        }
        battlefieldController.repaint();
    }
}
