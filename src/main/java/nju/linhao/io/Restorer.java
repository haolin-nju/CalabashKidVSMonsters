package main.java.nju.linhao.io;

import javafx.stage.FileChooser;
import main.java.nju.linhao.utils.Log;

import java.io.*;
import java.util.LinkedList;

public class Restorer {
    private static Restorer restorer = new Restorer();
    private static final FileChooser fileChooser = new FileChooser();
    static{
        fileChooser.setInitialDirectory(new File("./"));
        fileChooser.setTitle("打开战斗记录");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
    }

    private Restorer(){
    }

    public static Restorer getInstance(){
        return restorer;
    }

    public LinkedList<Log> restore() {
        File restoreFile = fileChooser.showOpenDialog(null);
        if(restoreFile != null){
            LinkedList<Log> logs = new LinkedList<>();
            ObjectInputStream objectInputStream = null;
            try {
                objectInputStream = new ObjectInputStream(new FileInputStream(restoreFile));
                while (true) {
                    try {
                        Log log = (Log) objectInputStream.readObject();
                        logs.add(log);
                    } catch (ClassNotFoundException | IOException e) {
                        break;
                    }
                }
                objectInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return logs;
        }
        return null;
    }


}
