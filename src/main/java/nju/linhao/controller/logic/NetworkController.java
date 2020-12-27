package main.java.nju.linhao.controller.logic;

import main.java.nju.linhao.creature.Creature;
import main.java.nju.linhao.enums.*;
import main.java.nju.linhao.team.HumanTeam;
import main.java.nju.linhao.team.MonsterTeam;
import main.java.nju.linhao.team.Team;
import main.java.nju.linhao.utils.Configuration;
import main.java.nju.linhao.utils.Message;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class NetworkController implements Runnable {
    private static InetAddress inetAddress;
    private static String localName;
    private static String localIp;

    private static String destIp;
    private static int destPort;

    private static Socket socketToServer;
    private static Socket socketToClient;

    private static boolean isCurrentClientServer;
    private static boolean connectionEstablished;

    public NetworkController() throws UnknownHostException {
        inetAddress = InetAddress.getLocalHost();
        localName = inetAddress.getHostName();
        localIp = inetAddress.getHostAddress();
        destIp = inetAddress.getHostAddress();
        destPort = Configuration.DEFAULT_PORT;
        isCurrentClientServer = false;
        connectionEstablished = false;
    }

    public String getLocalIp() {
        return localIp;
    }

    public String getLocalName() {
        return localName;
    }

    public void setDestIp(String destIp) {
        this.destIp = destIp;
    }

    public void sendMessage(MessageType messageType) throws IOException {
        Message msgToSend = new Message(messageType);
        send(msgToSend);
    }

    public void sendMessage(MessageType messageType, Object object) throws IOException {
        Message msgToSend = new Message(messageType, object);
        send(msgToSend);
    }

    private void send(Message msgToSend) throws IOException {
        ObjectOutputStream out = null;
        try {
            if (isCurrentClientServer) {//是服务器
                out = new ObjectOutputStream(socketToClient.getOutputStream());
            } else {
                out = new ObjectOutputStream(socketToServer.getOutputStream());
            }
            out.writeObject(msgToSend);
        } catch(IOException e) {
            LocalGameController.requestLogMessages("传输数据发生错误！");
            throw (IOException) e.fillInStackTrace();
        }
    }

    private void serveAsServer() throws IOException, ClassNotFoundException, InterruptedException {
        // 建立连接
        ServerSocket serverSocket = new ServerSocket(Configuration.DEFAULT_PORT);
        while(socketToClient == null){
            socketToClient = serverSocket.accept();
        }
        connectionEstablished = true;
        LocalGameController.requestLogMessages("本机同时作为服务器进行游戏");
    }

    private Message recvMessage() throws IOException, ClassNotFoundException {
        ObjectInputStream in = null;
        try {
            if (isCurrentClientServer) {//是服务器
                in = new ObjectInputStream(socketToClient.getInputStream());
            } else {
                in = new ObjectInputStream(socketToServer.getInputStream());
            }
            return (Message) in.readObject();
        } catch(IOException e){
            LocalGameController.requestLogMessages("传输数据发生错误！");
            throw (IOException) e.fillInStackTrace();
        }
    }

    private void parseMessage(Message message) throws IOException {
        MessageType msgType = message.getMessageType();
        Object msgContent = message.getMessageContent();
        switch (msgType) {
            case CLIENT_READY:// 服务器收到客户端建立连接请求
                sendMessage(MessageType.SERVER_ACK);// 回送服务器ACK，这里故意不写break的！
            case SERVER_ACK:// 客户端收到服务器确认请求
                // 给对方发送我方生物的Team信息，设置本地运行状态为RUN
                LocalGameController.setCurrentStatus(LocalGameStatus.RUN);
                sendMessage(MessageType.TEAM_CREATE, LocalGameController.requestGetTeamFormation());
                connectionEstablished = true;
                break;
            case TEAM_CREATE: //收到对方的创建对象的请求
                LocalGameController.requestSetTeamFormation((Formation) msgContent);
//                LocalGameController.requestStartCreatureThreads();//启动所有生物线程开始运行
                LocalGameController.requestLogMessages("游戏开始！");
                break;
            case CREATURE_MOVE:
                LocalGameController.requestOthersCreatureMove((Creature) msgContent);
                break;
            case CREATURE_ATTACK:
                break;
            default:
        }
    }

    @Override
    public void run() {
        // 首先，如果连接的是本机，那么自己就是服务器
        if (destIp == localIp || destIp == "127.0.0.1") {
            isCurrentClientServer = true;
            try (ServerSocket serverSocket = new ServerSocket(Configuration.DEFAULT_PORT)){

            } catch (IOException e){
                isCurrentClientServer = false;
            }
        }
        if(isCurrentClientServer == false) {
            // 其次，如果连接远程，默认对方是服务器，且该房间要存在
            try {
                while (true) {
                    socketToServer = new Socket(destIp, destPort);//请求对方是不是服务器
                    sendMessage(MessageType.CLIENT_READY);
                    Message recvMsg = recvMessage();
                    parseMessage(recvMsg);
                    if (connectionEstablished) {
                        isCurrentClientServer = false;
//                        LocalGameController.requestGameStart();
                        LocalGameController.requestLogMessages("本机仅作为客户端进行游戏");
                        break;
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("对方房间不存在！");
                LocalGameController.requestLogMessages("想要连接的房间不存在！");
            }
        }
        // 对各种信息进行处理
        try {
            if(isCurrentClientServer) {
                serveAsServer();
            }
            while (true) {
                Message recvMsg = recvMessage();
                parseMessage(recvMsg);
            }
        } catch (IOException | ClassNotFoundException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
