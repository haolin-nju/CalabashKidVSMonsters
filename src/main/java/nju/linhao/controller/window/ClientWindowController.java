package main.java.nju.linhao.controller.window;

import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;
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
import main.java.nju.linhao.enums.LocalGameStatus;
import main.java.nju.linhao.enums.MessageType;

public class ClientWindowController {
    private final ToggleGroup group = new ToggleGroup();

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="serverChoiceBox"
    private ChoiceBox<String> serverChoiceBox; // Value injected by FXMLLoader

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

    }

    @FXML
    void monsterRadioButtonOnClicked(MouseEvent event) {

    }

    @FXML
    void readyToFightButtonOnClicked(MouseEvent event) {
        String srcIp = NetworkController.getLocalIp();
        LocalGameController.setCurrentStatus(LocalGameStatus.CONNECTING);
        NetworkController.sendMessage(MessageType.CLIENT1_READY, srcIp, srcIp);
        LocalGameController.setCurrentStatus(LocalGameStatus.READY);
        ((Stage) readyToFightButton.getScene().getWindow()).close();
    }

    @FXML
    void returnToMainWindowOnClicked(MouseEvent event) {
        ((Stage) returnToMainWindow.getScene().getWindow()).close();
    }

    @FXML
    void serverChoiceBoxOnClicked(MouseEvent event) {

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() throws UnknownHostException {
        assert serverChoiceBox != null : "fx:id=\"serverChoiceBox\" was not injected: check your FXML file 'ClientWindow.fxml'.";
        assert humanRadioButton != null : "fx:id=\"humanRadioButton\" was not injected: check your FXML file 'ClientWindow.fxml'.";
        assert monsterRadioButton != null : "fx:id=\"monsterRadioButton\" was not injected: check your FXML file 'ClientWindow.fxml'.";
        assert readyToFightButton != null : "fx:id=\"readyToFightButton\" was not injected: check your FXML file 'ClientWindow.fxml'.";
        assert returnToMainWindow != null : "fx:id=\"returnToMainWindow\" was not injected: check your FXML file 'ClientWindow.fxml'.";

        humanRadioButton.setToggleGroup(group);
        humanRadioButton.setSelected(true);

        monsterRadioButton.setToggleGroup(group);

        NetworkController.init();
        serverChoiceBox.getItems().add(NetworkController.getLocalName());
        serverChoiceBox.getSelectionModel().selectFirst();
    }
}
