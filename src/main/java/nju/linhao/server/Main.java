package main.java.nju.linhao.server;

import main.java.nju.linhao.server.controller.NetWorkController;
import main.java.nju.linhao.server.controller.SocketController;

import java.io.IOException;

public class Main {
    public static SocketController socket1 = new SocketController();
    public static SocketController socket2 = new SocketController();
    public static final int[] map = new int[]{-1,-1}; //维护玩家和socket的映射
    public static final String[] table = new String[]{"PLAYER_1","PLAYER_2"};

    public static void main(String[] args) throws IOException {
        NetWorkController netWorkController = new NetWorkController();
        Thread listening = new Thread(netWorkController); // 长连接建立侦听
        Thread t1 = new Thread(socket1);
        Thread t2 = new Thread(socket2);
        listening.start();
        t1.start();
        t2.start();
    }
}
