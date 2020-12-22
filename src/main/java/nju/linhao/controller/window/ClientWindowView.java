package main.java.nju.linhao.controller.window;

import java.io.IOException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import main.java.nju.linhao.controller.logic.LocalGameController;
import main.java.nju.linhao.controller.logic.NetworkController;
import main.java.nju.linhao.enums.*;

public class ClientWindowView {
    private ToggleGroup group = new ToggleGroup();

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="serverChoiceBox"
    private ChoiceBox<String> serverChoiceBox; // Value injected by FXMLLoader

    @FXML // fx:id="formationChoiceBox"
    private ChoiceBox<Formation> formationChoiceBox; // Value injected by FXMLLoader

    @FXML // fx:id="humanRadioButton"
    private RadioButton humanRadioButton; // Value injected by FXMLLoader

    @FXML // fx:id="monsterRadioButton"
    private RadioButton monsterRadioButton; // Value injected by FXMLLoader

    @FXML // fx:id="readyToFightButton"
    private Button readyToFightButton; // Value injected by FXMLLoader

    @FXML // fx:id="returnToMainWindow"
    private Button returnToMainWindow; // Value injected by FXMLLoader

    @FXML
    void humanRadioButtonOnClicked(MouseEvent event) {
        LocalGameController.requestSetLocalPlayer(Player.PLAYER_1);
        formationChoiceBox.getSelectionModel().clearSelection();
    }

    @FXML
    void monsterRadioButtonOnClicked(MouseEvent event) {
        LocalGameController.requestSetLocalPlayer(Player.PLAYER_2);
        formationChoiceBox.getSelectionModel().clearSelection();
    }

    @FXML
    void readyToFightButtonOnClicked(MouseEvent event) throws InterruptedException {
        String srcIp = NetworkController.getLocalIp();
        LocalGameController.setCurrentStatus(LocalGameStatus.CONNECTING);

        boolean isSucceed = NetworkController.sendMessage(MessageType.CLIENT1_READY, "null");
        if (!isSucceed) {
            LocalGameController.changeLocalPlayer();
            //可能需要一些提示信息
        }

        LocalGameController.setCurrentStatus(LocalGameStatus.READY);
        ((Stage) readyToFightButton.getScene().getWindow()).close();
    }

    @FXML
    void returnToMainWindowOnClicked(MouseEvent event) {
        LocalGameController.requestClearInfo();
        ((Stage) returnToMainWindow.getScene().getWindow()).close();
    }

    @FXML
    void serverChoiceBoxOnClicked(MouseEvent event) {

    }

    @FXML
    void formationChoiceBoxOnClicked(MouseEvent event) {

    }

    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() throws IOException {
        assert serverChoiceBox != null : "fx:id=\"serverChoiceBox\" was not injected: check your FXML file 'ClientWindow.fxml'.";
        assert formationChoiceBox != null : "fx:id=\"formationChoiceBox\" was not injected: check your FXML file 'ClientWindow.fxml'.";
        assert humanRadioButton != null : "fx:id=\"humanRadioButton\" was not injected: check your FXML file 'ClientWindow.fxml'.";
        assert monsterRadioButton != null : "fx:id=\"monsterRadioButton\" was not injected: check your FXML file 'ClientWindow.fxml'.";
        assert readyToFightButton != null : "fx:id=\"readyToFightButton\" was not injected: check your FXML file 'ClientWindow.fxml'.";
        assert returnToMainWindow != null : "fx:id=\"returnToMainWindow\" was not injected: check your FXML file 'ClientWindow.fxml'.";

        humanRadioButton.setToggleGroup(group);
        humanRadioButton.setUserData("人类阵营");
        humanRadioButton.setSelected(true);

        monsterRadioButton.setToggleGroup(group);
        monsterRadioButton.setUserData("妖怪阵营");

        readyToFightButton.setDisable(true);

        NetworkController.init();
        serverChoiceBox.getItems().add(NetworkController.getLocalName());
        serverChoiceBox.getSelectionModel().selectFirst();

        formationChoiceBox.getItems().addAll(
                Formation.LONG_SNAKE_FORMATION,
                Formation.FRONTAL_VECTOR_FORMATION,
                Formation.SQUARE_FORMATION);
        formationChoiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if((int) newValue == -1){
                    formationChoiceBox.getSelectionModel().selectFirst();
                }
                else{
                    FormationRequest newFormationRequest = FormationRequest.values()[(int) newValue + 2];
                    LocalGameController.requestSetFormation(newFormationRequest);
                    readyToFightButton.setDisable(false);
                }
            }
        });

        System.out.println("333");
    }
}
