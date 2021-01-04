package main.java.nju.linhao.controller.logic;

import javafx.application.HostServices;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import main.java.nju.linhao.battlefield.Battlefield;
import main.java.nju.linhao.battlefield.BattlefieldController;
import main.java.nju.linhao.bullet.Bullet;
import main.java.nju.linhao.controller.window.ClientWindowView;
import main.java.nju.linhao.controller.window.MainWindowView;
import main.java.nju.linhao.creature.Creature;
import main.java.nju.linhao.enums.*;
import main.java.nju.linhao.exception.OutofRangeException;
import main.java.nju.linhao.io.Recorder;
import main.java.nju.linhao.utils.Configuration;
import main.java.nju.linhao.utils.ImageLoader;
import main.java.nju.linhao.utils.Log;

import java.io.IOException;
import java.util.ArrayList;


public class LocalGameController {
    private LocalGameStatus currStatus;
    private Scene localClientScene;
    private Image localIcon;
    private Player localPlayer;
    private Player otherPlayer;
    private Creature currCreatureSelected;
    private HostServices hostServices;
    private MainWindowView mainWindowView;
    private ClientWindowView clientWindowView;
    private BattlefieldController battlefieldController;
    private NetworkController networkController;
    private ArrayList<Thread> allThreads;
    private int localCreaturesAliveCnt;
    private boolean isOtherLost;
    private Image statusImg;
    private boolean isLocalServer;
    private Recorder recorder;


    private static LocalGameController localGameController = new LocalGameController();

    private LocalGameController() {
    }

    public static LocalGameController getInstance() {
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
        allThreads = new ArrayList<>();
        isLocalServer = false;
    }

    public LocalGameStatus getCurrentStatus() {
        return currStatus;
    }

    public void setCurrentStatusWithoutCondition(LocalGameStatus status){
        this.currStatus = status;
    }

    public void setCurrentStatus(LocalGameStatus status) {
        // state machine
        switch (status) {
            case INIT:
                if (currStatus == LocalGameStatus.INIT
                        || currStatus == LocalGameStatus.END
                        || currStatus == LocalGameStatus.CONNECTING) {
                    currStatus = LocalGameStatus.INIT;
                    recorder = Recorder.getInstance();
                }
//                else {
//                    System.out.println("未定义的状态机！原状态：" + currStatus + "目标状态：INIT");
//                }
                break;
            case CONNECTING:
                if (currStatus == LocalGameStatus.INIT) {
                    currStatus = LocalGameStatus.CONNECTING;
                }
//                else {
//                    System.out.println("未定义的状态机！原状态：" + currStatus + "目标状态：CONNECTING");
//                }
                break;
            case READY:
                if (currStatus == LocalGameStatus.CONNECTING) {
                    currStatus = LocalGameStatus.READY;
                }
//                else {
//                    System.out.println("未定义的状态机！原状态：" + currStatus + "目标状态：READY");
//                }
                break;
            case RUN:
                if (currStatus == LocalGameStatus.READY) {
                    currStatus = LocalGameStatus.RUN;
                }
//                else {
//                    System.out.println("未定义的状态机！原状态：" + currStatus + "目标状态：RUN");
//                }
                break;
            case WE_LOSE:
                if (currStatus == LocalGameStatus.RUN) {
//                        || currStatus == LocalGameStatus.PAUSE){
                    currStatus = LocalGameStatus.WE_LOSE;
                }
//                else {
//                    System.out.println("未定义的状态机！原状态：" + currStatus + "目标状态：WE_LOSE");
//                }
                break;
            case WE_WIN:
                if (currStatus == LocalGameStatus.RUN) {
//                        || currStatus == LocalGameStatus.PAUSE){
                    currStatus = LocalGameStatus.WE_WIN;
                }
//                else {
//                    System.out.println("未定义的状态机！原状态：" + currStatus + "目标状态：WE_WIN");
//                }
                break;
            case END:
                if(currStatus == LocalGameStatus.WE_LOSE || currStatus == LocalGameStatus.WE_WIN) {
                    currStatus = LocalGameStatus.END;
                }
//                else {
//                    System.out.println("未定义的状态机！原状态：" + currStatus + "目标状态：END" );
//                }
                break;
            default:
                break;
        }
    }

    // Basic Game Logic
    public void newGame() {
        setCurrentStatus(LocalGameStatus.INIT);
        getReady();
        mainWindowView.logMessages("开始新游戏！");
    }

    public void resetGame() {
        mainWindowView.logMessages("重置游戏！！");
        setCurrentStatus(LocalGameStatus.READY);
    }

//    public void pauseGame() {
//        mainWindowView.logMessages("暂停游戏！！");
//        setCurrentStatus(LocalGameStatus.PAUSE);
//    }
//
//    public void continueGame() {
//        mainWindowView.logMessages("继续游戏！！");
//        setCurrentStatus(LocalGameStatus.RUN);
//    }

    public void endGame(LocalGameStatus localGameStatus) {
        mainWindowView.logMessages("游戏结束！");
        setCurrentStatus(localGameStatus);
        if(localGameStatus == LocalGameStatus.WE_LOSE) {
            try {
                networkController.sendMessage(MessageType.SOMEONE_LOSE);
            } catch (IOException e) {
                requestLogMessages("失败信息没有被另一端接收！");
            }
            requestRecordLog(LogType.SOMEONE_LOSE, "null");
        }
        statusImg = ImageLoader.getInstance().loadGameStatusImg(currStatus);
        battlefieldController.repaint();
    }

    public void requestOtherLose() {
        isOtherLost = true;
        otherRequestRecordLog(LogType.SOMEONE_LOSE, "null");
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
            if(currStatus == LocalGameStatus.READY) {
                if (localPlayer == Player.PLAYER_1) {
                    mainWindowView.logMessages("本机阵营：人类阵营");
                    otherPlayer = Player.PLAYER_2;
                } else {
                    mainWindowView.logMessages("本机阵营：妖怪阵营");
                    otherPlayer = Player.PLAYER_1;
                }
                mainWindowView.logMessages("您还可以按'⬅''➡'键切换阵型！");
                mainWindowView.setNewGameDisable();
                battlefieldController.setDefaultSelectedCreature();
                battlefieldController.repaint();
                localCreaturesAliveCnt = battlefieldController.getLocalCreaturesAliveCnt();
                isOtherLost = false;
                if (localCreaturesAliveCnt == -1) {
                    mainWindowView.logMessages("初始化的本地生物数量有误！请检查！");
                }
            }
        });
        clientStage.show();
    }

    public synchronized int localAliveCreaturesDec() {
        if(localCreaturesAliveCnt > 0){
            --localCreaturesAliveCnt;
        }
        return localCreaturesAliveCnt;
    }

    public void requestNetworkController(String destIp) {
        networkController.setDestIp(destIp);
        Thread networkThread = new Thread(networkController);
        networkThread.start();
        allThreads.add(networkThread);
    }

    public void requestGameStart() {
        LocalGameController.getInstance().setCurrentStatus(LocalGameStatus.READY);
    }

    public String getLocalIp() {
        return networkController.getLocalIp();
    }

    public Player getLocalPlayer() {
        return localPlayer;
    }

    public BattlefieldController getBattlefieldController() {
        return battlefieldController;
    }

    public void setStatusImg(Image statusImg){
        this.statusImg = statusImg;
    }

    public Image getStatusImg(){
        return statusImg;
    }

    public boolean queryIsOtherLost(){
        return this.isOtherLost;
    }

    public void setIsLocalServer(boolean isLocalServer){
        this.isLocalServer = isLocalServer;
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

    public void requestSwitchFormation(FormationRequest formationRequest) {
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

    public void requestSwitchCurSelectedCreature(CreatureSwitchRequest forward) {
        //TODO: implement creature selection to boost game experience
        switch(forward){
            case FORWARD:
                System.out.println("向前选择生物");
            case BACKWARD:
                System.out.println("向后选择生物");
        }
    }

    public void requestClearInfo() {
        battlefieldController.clear();
    }

    public void requestRepaint() {
        battlefieldController.repaint();
    }

    public void requestMouseClick(double clickPosX, double clickPosY) throws OutofRangeException {
        // TODO: Mouse click event handle, including select a creature or attack. You can use Battlefirld.getCreatureFromPos() and utilize 泛型
        if(currStatus == LocalGameStatus.RUN)
            battlefieldController.requestMouseClick(clickPosX, clickPosY, localPlayer);
        else if(currStatus == LocalGameStatus.WE_LOSE || currStatus == LocalGameStatus.WE_WIN){
            setCurrentStatus(LocalGameStatus.END);
            battlefieldController.clear();
            networkController.stop();
        }
    }

    public Formation requestGetTeamFormation() {
        if (localPlayer == Player.PLAYER_1) {
            return battlefieldController.getBattlefield().getHumanTeam().getFormation();
        } else if (localPlayer == Player.PLAYER_2) {
            return battlefieldController.getBattlefield().getMonsterTeam().getFormation();
        } else {
            System.err.println("意外的本地玩家！");
            return null;
        }
    }

    public void requestSetTeamFormation(Formation formation) {
        // 为对方在本机设置阵型
        if (localPlayer == Player.PLAYER_1) {
            battlefieldController.getBattlefield().getMonsterTeam().setFormation(formation);
        } else if (localPlayer == Player.PLAYER_2) {
            battlefieldController.getBattlefield().getHumanTeam().setFormation(formation);
        } else {
            System.err.println("意料之外的阵营！");
        }
        battlefieldController.repaint();
        otherRequestRecordLog(LogType.TEAM_CREATE, formation);
    }

    public void requestStartCreatureAndBulletThreads() {
        allThreads.addAll(battlefieldController.getBattlefield().startLocalCreatureThreads(localPlayer));
        allThreads.add(battlefieldController.getBattlefield().startLocalBulletManagerThreads());
    }

    public void requestCreatureMove(Direction direction) {
        Creature curSelectedCreature = battlefieldController.letCurSelectedCreatureMove(direction);
        battlefieldController.repaint();
        try {
            networkController.sendMessage(MessageType.CREATURE_MOVE, curSelectedCreature);
        } catch (IOException e) {
            requestLogMessages("当前移动没有被另一端接收！");
        }
        requestRecordLog(LogType.CREATURE_MOVE, curSelectedCreature);
    }

    public void requestCreatureMove(Creature creature, Direction direction) {
        battlefieldController.letCreatureMove(creature, direction);
        battlefieldController.repaint();
        try {
            networkController.sendMessage(MessageType.CREATURE_MOVE, creature);
        } catch (IOException e) {
            requestLogMessages("当前移动没有被另一端接收！");
        }
        requestRecordLog(LogType.CREATURE_MOVE, creature);
    }

    public void requestOthersCreatureMove(Creature creature) {
        Battlefield battlefield = battlefieldController.getBattlefield();
        Creature creatureToMove = null;
        synchronized (battlefield) {
            creatureToMove = battlefield.getOtherCreatureFromId(creature, localPlayer);
            int[] newPos = creature.getPos();
            creatureToMove.setPos(newPos[0], newPos[1]);
        }
        battlefieldController.repaint();
        otherRequestRecordLog(LogType.CREATURE_MOVE, creature);
    }

    public void requestCreatureAttack(Bullet bullet) {
        try{
            networkController.sendMessage(MessageType.CREATURE_ATTACK, bullet);
        } catch (IOException e){
            requestLogMessages("当前攻击没有被另一端接收！");
        }
        requestRecordLog(LogType.BULLET_CREATE, bullet);// 记录生物攻击信息
    }

    public void requestOthersCreatureAttack(Bullet bullet){
        battlefieldController.getBattlefield().addBullet(bullet);
        otherRequestRecordLog(LogType.BULLET_CREATE, bullet);
    }

    public void requestSendCreatureRemainHealth(Creature creature) {
        try{
            networkController.sendMessage(MessageType.CREATURE_INJURED, creature);
        } catch (IOException e){
            requestLogMessages("当前伤害没有被另一端接收！");
        }
        requestRecordLog(LogType.CREATURE_INJURED, creature);
    }

    public void requestLocalCreatureRemainHealth(Creature creature) {
        Battlefield battlefield = battlefieldController.getBattlefield();
        synchronized (battlefield){
            Creature creatureToSetRemainHealth = battlefield.getLocalCreatureFromId(creature, localPlayer);
            double remainHealth = creature.getHealth();
            creatureToSetRemainHealth.setHealth(remainHealth);
            otherRequestRecordLog(LogType.CREATURE_INJURED, creature);
        }
    }

    public void requestSendBulletDestroy(Bullet bullet) {
        try{
            networkController.sendMessage(MessageType.BULLET_DESTROY, bullet);
        }catch (IOException e){
            requestLogMessages("当前伤害没有被另一端接收！");
        }
        requestRecordLog(LogType.BULLET_DESTROY, bullet);
    }

    public void requestLocalBulletDestroy(Bullet bullet) {
        battlefieldController.getBattlefield().getBulletManager().removeBullet(bullet);
        otherRequestRecordLog(LogType.BULLET_DESTROY, bullet);
    }

    public void requestCheckLocalPlayer(Player player) {
        Player[] players = Player.values();
        if(player == localPlayer){
            mainWindowView.logMessages("您选择了服务器选择的阵容！\n将自动为您切换到另一个阵营和初始阵型！");
            for(Player curPlayer : players){
                if(curPlayer != player){
                    localPlayer = curPlayer;
                    battlefieldController.setLocalPlayer(localPlayer);
                }
            }
        }
    }

    public void requestBuildRecorder(){
        recorder.init(Configuration.DEFAULT_RECORD_DIR_PATH);
    }

    public void requestRecordLog(LogType logType, Object logContent){
        if(logType == LogType.CREATURE_MOVE){
            Creature tempCreature = (Creature) logContent;
            int[] posArray = tempCreature.getPos();
            Recorder.getInstance().recordLog(new Log(localPlayer,
                    logType,
                    tempCreature.getCreatureName() + ":" + posArray[0] + "," + posArray[1]));
        } else if(logType == LogType.CREATURE_INJURED) {
            Creature tempCreature = (Creature) logContent;
            double restHealth = tempCreature.getHealth();
            Recorder.getInstance().recordLog(new Log(localPlayer,
                    logType,
                    tempCreature.getCreatureName() + ":" + restHealth));
        } else {
            Recorder.getInstance().recordLog(new Log(localPlayer, logType, logContent));
        }
    }

    public void otherRequestRecordLog(LogType logType, Object logContent) {
        if(logType == LogType.CREATURE_MOVE){
            Creature tempCreature = (Creature) logContent;
            int[] posArray = tempCreature.getPos();
            Recorder.getInstance().recordLog(new Log(otherPlayer,
                    logType,
                    tempCreature.getCreatureName() + ":" + posArray[0] + "," + posArray[1]));
        } else if(logType == LogType.CREATURE_INJURED) {
            Creature tempCreature = (Creature) logContent;
            double restHealth = tempCreature.getHealth();
            Recorder.getInstance().recordLog(new Log(otherPlayer,
                    logType,
                    tempCreature.getCreatureName() + ":" + restHealth));
        } else {
            Recorder.getInstance().recordLog(new Log(otherPlayer, logType, logContent));
        }
    }

    public void requestSaveRecord(){
//        recorder.writeLog();
    }
}
