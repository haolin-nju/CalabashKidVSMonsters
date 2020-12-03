package main.java.nju.linhao.io;

import javafx.stage.FileChooser;

import java.io.File;

public class Restorer {
    private FileChooser fileChooser;

    public Restorer(){
        fileChooser = new FileChooser();
    };

    public boolean restore(){
        fileChooser.setTitle("打开战斗记录");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File loadedFile = fileChooser.showOpenDialog(null);
        return true;
    }
}
