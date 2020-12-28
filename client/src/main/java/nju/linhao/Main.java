package main.java.nju.linhao;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import main.java.nju.linhao.battlefield.Battlefield;
import main.java.nju.linhao.battlefield.BattlefieldController;
import main.java.nju.linhao.controller.window.*;
import main.java.nju.linhao.controller.logic.*;
import main.java.nju.linhao.enums.FormationRequest;
import main.java.nju.linhao.enums.LocalGameStatus;
import main.java.nju.linhao.io.Restorer;
import sun.nio.ch.Net;

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
        LocalGameController.init(
                mainWindowView,
                clientWindowLoader.getController(),
                new BattlefieldController(new Battlefield(), mainWindowView),
                new NetworkController(),
                clientScene,
                icon,
                hostServices);
        NetworkController networkController=new NetworkController();
        Thread networkThread = new Thread(networkController);
        networkThread.start();

        scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.SPACE) {
                if (LocalGameController.getCurrentStatus() == LocalGameStatus.END) {
                    LocalGameController.resetGame();
                } else if (LocalGameController.getCurrentStatus() == LocalGameStatus.INIT) {
                    LocalGameController.newGame();
                }
            }
            else if (event.getCode() == KeyCode.L) {
                if (LocalGameController.getCurrentStatus() == LocalGameStatus.END
                        || LocalGameController.getCurrentStatus() == LocalGameStatus.READY) {
                    Restorer.restore();
                }
            }
            else if (event.getCode() == KeyCode.LEFT && LocalGameController.getCurrentStatus() == LocalGameStatus.READY){
                LocalGameController.requestSetFormation(FormationRequest.BACKWARD);
            }
            else if(event.getCode() == KeyCode.RIGHT && LocalGameController.getCurrentStatus() == LocalGameStatus.READY){
                LocalGameController.requestSetFormation(FormationRequest.FORWARD);
            }
        });

        scene.getStylesheets().add(Main.class.getResource("/css/Style.css").toExternalForm());
        primaryStage.setTitle("葫芦娃大战妖精");
        primaryStage.getIcons().add(icon);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {

        launch(args);

    }

}
