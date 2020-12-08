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

import main.java.nju.linhao.controller.window.*;
import main.java.nju.linhao.controller.logic.*;
import main.java.nju.linhao.enums.LocalGameStatus;
import main.java.nju.linhao.io.Restorer;
import sun.nio.ch.Net;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Class<?> curClass = this.getClass();

        // load main window
        FXMLLoader mainWindowLoader = new FXMLLoader(curClass.getResource("/MainWindow.fxml"));
        Parent parent = mainWindowLoader.load();
        Scene scene = new Scene(parent);

        // load client window
        FXMLLoader clientWindowLoader = new FXMLLoader(curClass.getResource("/ClientWindow.fxml"));
        Parent clientWindow = clientWindowLoader.load();
        Scene clientScene = new Scene(clientWindow);

        FXMLLoader battlefieldLoader = new FXMLLoader(curClass.getResource("/Battlefield.fxml"));
        Parent battlefieldCanvas = battlefieldLoader.load();
        Scene battlefieldScene = new Scene(battlefieldCanvas);

        HostServices hostServices = this.getHostServices();

        Image icon = new Image(curClass.getResourceAsStream("/icon/CalabashKidsVSMonstersIcon.png"));
        LocalGameController.init(
                mainWindowLoader.getController(),
                clientWindowLoader.getController(),
                battlefieldLoader.getController(),
                clientScene,
                icon,
                hostServices);

        scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.SPACE) {
                if (LocalGameController.getCurrentStatus() == LocalGameStatus.END) {
                    LocalGameController.resetGame();
                } else if (LocalGameController.getCurrentStatus() == LocalGameStatus.INIT) {
                    LocalGameController.newGame();
                }
            }
            if (event.getCode() == KeyCode.L) {
                if (LocalGameController.getCurrentStatus() == LocalGameStatus.END
                        || LocalGameController.getCurrentStatus() == LocalGameStatus.READY) {
                    Restorer.restore();
                }

            }
        });

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
