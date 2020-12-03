package main.java.nju.linhao;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {
    private static final int CANVAS_WIDTH = 400;
    private static final int CANVAS_HEIGHT = 300;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Class<?> curClass = this.getClass();
        Scene scene = WindowInit(primaryStage, curClass);
        primaryStage.setTitle("CalabashKids VS Monsters");
        primaryStage.getIcons()
                .add(new Image(curClass.getResourceAsStream("/icon/CalabashKidsVSMonstersIcon.png")
                ));
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private Scene WindowInit(Stage primaryStage, Class<?> curClass) throws Exception{
        VBox root = FXMLLoader.load(curClass.getResource("/MainWindow.fxml"));

        final Image img = new Image(curClass.getResourceAsStream("/pictures/background.jpg"));
        final Canvas canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
        root.getChildren().add(canvas);
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.save();
        graphicsContext.drawImage(img, 0, 0);
        graphicsContext.restore();

        Button startButton = new Button("开始战斗");
        Button openButton = new Button("回放记录");

        HBox hBox = new HBox(50);
        hBox.setPadding(new Insets(0, 100, 0, 100));
        hBox.getChildren().add(startButton);
        hBox.getChildren().add(openButton);
        root.getChildren().add(hBox);

        Scene scene = new Scene(root);
        return scene;
    }
}
