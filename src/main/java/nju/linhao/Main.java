package main.java.nju.linhao;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import main.java.nju.linhao.battlefield.Battlefield;
import main.java.nju.linhao.battlefield.BattlefieldController;
import main.java.nju.linhao.controller.window.*;
import main.java.nju.linhao.controller.logic.*;
import main.java.nju.linhao.enums.CreatureSwitchRequest;
import main.java.nju.linhao.enums.Direction;
import main.java.nju.linhao.enums.FormationRequest;
import main.java.nju.linhao.enums.LocalGameStatus;
import main.java.nju.linhao.utils.Displayer;
import main.java.nju.linhao.io.Restorer;
import main.java.nju.linhao.io.Log;

import java.util.LinkedList;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Class<?> curClass = this.getClass();

        // load main window
        FXMLLoader mainWindowLoader = new FXMLLoader(curClass.getResource("/fxml/MainWindow.fxml"));
        Parent parent = mainWindowLoader.load();
        Scene scene = new Scene(parent);

        // load client window
        FXMLLoader clientWindowLoader = new FXMLLoader(curClass.getResource("/fxml/ClientWindow.fxml"));
        Parent clientWindow = clientWindowLoader.load();
        Scene clientScene = new Scene(clientWindow);

        HostServices hostServices = this.getHostServices();

        Image icon = new Image(curClass.getResourceAsStream("/icon/CalabashKidsVSMonstersIcon.png"));
        MainWindowView mainWindowView = mainWindowLoader.getController();
        BattlefieldController battlefieldController = new BattlefieldController(new Battlefield(), mainWindowView);
        LocalGameController.getInstance().init(
                mainWindowView,
                clientWindowLoader.getController(),
                battlefieldController,
                new NetworkController(),
                clientScene,
                icon,
                hostServices);

        scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            LocalGameStatus curGameStatus = LocalGameController.getInstance().getCurrentStatus();
            switch (event.getCode()) {
                case SPACE:
                    if (curGameStatus == LocalGameStatus.INIT) {
                        LocalGameController.getInstance().newGame();
                    }
//                    else if (curGameStatus == LocalGameStatus.END) {
//                        LocalGameController.getInstance().resetGame();
//                    }
                    break;
                case L:
                    if (curGameStatus == LocalGameStatus.END
                            || curGameStatus == LocalGameStatus.INIT) {
                        LinkedList<Log> logs = Restorer.getInstance().restore();
                        Displayer.getInstance().init(new BattlefieldController(new Battlefield(), mainWindowView), logs);
                        Thread displayThread = new Thread(Displayer.getInstance());
                        displayThread.start();
                    }
                    break;
                case LEFT:
                    if (curGameStatus == LocalGameStatus.READY) {
                        LocalGameController.getInstance().requestSwitchFormation(FormationRequest.BACKWARD);
                    }
                    break;
                case RIGHT:
                    if (curGameStatus == LocalGameStatus.READY) {
                        LocalGameController.getInstance().requestSwitchFormation(FormationRequest.FORWARD);
                    }
                    break;
                case W:
                    if (curGameStatus == LocalGameStatus.RUN) {
                        LocalGameController.getInstance().requestCreatureMove(Direction.UP);
                    }
                    break;
                case S:
                    if (curGameStatus == LocalGameStatus.RUN) {
                        LocalGameController.getInstance().requestCreatureMove(Direction.DOWN);
                    }
                    break;
                case A:
                    if (curGameStatus == LocalGameStatus.RUN) {
                        LocalGameController.getInstance().requestCreatureMove(Direction.LEFT);
                    }
                    break;
                case D:
                    if (curGameStatus == LocalGameStatus.RUN) {
                        LocalGameController.getInstance().requestCreatureMove(Direction.RIGHT);
                    }
                    break;
                case Q:
                    if(curGameStatus == LocalGameStatus.RUN){
                        LocalGameController.getInstance().requestSwitchCurSelectedCreature(CreatureSwitchRequest.FORWARD);
                    }
                case E:
                    if(curGameStatus == LocalGameStatus.RUN){
                        LocalGameController.getInstance().requestSwitchCurSelectedCreature(CreatureSwitchRequest.BACKWARD);
                    }
                default:
                    break;
            }
        });

        scene.getStylesheets().add(Main.class.getResource("/css/Style.css").toExternalForm());
        primaryStage.setTitle("葫芦娃大战妖精");
        primaryStage.setOnCloseRequest((event) -> Platform.exit());
        primaryStage.getIcons().add(icon);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
