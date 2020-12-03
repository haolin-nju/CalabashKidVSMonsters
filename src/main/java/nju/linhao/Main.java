package main.java.nju.linhao;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Class<?> curClass = this.getClass();
        FXMLLoader loader = new FXMLLoader(curClass.getResource("/MainWindow.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);

        Controller controller = loader.getController();
        controller.setHostController(getHostServices());

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

}
