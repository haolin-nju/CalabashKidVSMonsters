package main.java.nju.linhao.server.controller;

import main.java.nju.linhao.server.Main;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.function.DoubleToIntFunction;

public class NetWorkController implements Runnable {
    static ArrayList<String> msg1;//消息队列
    static ArrayList<String> msg2;//消息队列
    static ServerSocket serverSocket;

    public NetWorkController() throws IOException {
        msg1 = new ArrayList<>();
        msg2 = new ArrayList<>();
        serverSocket = new ServerSocket(7777);

    }

    @Override
    public void run() {
        while (Main.socket1.getSocket()==null){
            try {
                Main.socket1.setSocket(serverSocket.accept());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Main.socket1);
        System.out.println("连接1已建立");
        System.out.println(Main.socket2.getSocket());
        System.out.println(serverSocket);
        while(Main.socket2.getSocket()==null){
            try {
                Main.socket2.setSocket(serverSocket.accept());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("连接2已建立");
        return;
    }
}
