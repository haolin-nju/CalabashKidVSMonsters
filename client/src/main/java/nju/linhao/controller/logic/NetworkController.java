package main.java.nju.linhao.controller.logic;

import main.java.nju.linhao.enums.LocalGameStatus;
import main.java.nju.linhao.enums.MessageType;
import main.java.nju.linhao.enums.Player;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.Semaphore;

public class NetworkController implements Runnable {
    private static String localName;
    private static String localIp;
    private static Socket socket;
    private static ArrayList<String> command;//command用于接收对方传来的指令, 可能要改成并发安全的对象
    private static final Stack<String> st = new Stack<>();
    private static final Stack<String> st2 = new Stack<>();

    private static NetworkController instance = null;

    public static NetworkController getInstance() {
        if (instance == null) {
            synchronized (NetworkController.class) {
                if (instance == null) {
                    instance = new NetworkController();
                }
            }
        }
        return instance;
    }


    public static void init() throws IOException {
        InetAddress inetAddress = InetAddress.getLocalHost();
        localName = inetAddress.getHostName();
        localIp = inetAddress.getHostAddress();

        command = new ArrayList<>();



        int destPort = 7777;
        String destIp = "127.0.0.1";
        socket = new Socket(destIp, destPort);
    }

    public static String getLocalIp() {
        return localIp;
    }

    public static String getLocalName() {
        return localName;
    }

    //加锁使得sendMessage一次只能被一个线程调用
    public synchronized static boolean sendMessage(MessageType messageType, String message) throws InterruptedException {
        // TODO: 发送message
        String sendMsg="";
        switch (messageType) {
            case CLIENT1_READY:
                sendMsg = sendMsg + messageType + "#" + (
                        LocalGameController.getLocalPlayer().equals(Player.PLAYER_1)?"PLAYER_1":"PLAYER2");
                break;
            // TODO: more case

        }
        //st 始终只能由一个元素，算是共享内存区，由于直接修改String对象会带来不必要麻烦
        st.push(sendMsg);
        System.out.println("here");
        synchronized (st2) {


            // After successfully connected
            st2.wait();
            LocalGameController.requestLogMessages("已经向服务器发送准备请求！");
            return st2.pop().equals("SUCCESS");
        }
    }





    @Override
    public void run() {
        while(true) {
            System.out.println(socket);
            try {
                if (st.isEmpty()) {
                    if (LocalGameController.getCurrentStatus().equals(
                            LocalGameStatus.READY)) {
                        OutputStream outToServer = socket.getOutputStream();
                        DataOutputStream out = new DataOutputStream(outToServer);
                        out.writeUTF("FETCH#"+(
                                LocalGameController.getLocalPlayer().equals(Player.PLAYER_1)?"PLAYER_1":"PLAYER2"));

                        InputStream inFromServer = socket.getInputStream();
                        DataInputStream in = new DataInputStream(inFromServer);
                        command.add(in.readUTF());
                    }
                } else {
                    System.out.println("there");
                    OutputStream outToServer = socket.getOutputStream();
                    DataOutputStream out = new DataOutputStream(outToServer);
                    out.writeUTF(st.pop());

                    InputStream inFromServer = socket.getInputStream();
                    DataInputStream in = new DataInputStream(inFromServer);
                    synchronized (st2) {
                        st2.push(in.readUTF());
                        st2.notify();
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
