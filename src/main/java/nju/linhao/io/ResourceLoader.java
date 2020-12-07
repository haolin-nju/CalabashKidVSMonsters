package main.java.nju.linhao.io;

import javafx.fxml.FXMLLoader;

public class ResourceLoader {
    public static FXMLLoader loadFXML(Class<?> curClass){
        return new FXMLLoader(curClass.getResource("/MainWindow.fxml"));
    }
}
