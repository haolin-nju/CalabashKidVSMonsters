package main.java.nju.linhao.controller.logic;

import main.java.nju.linhao.enums.MessageType;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class NetworkController {
    private static InetAddress inetAddress;
    private static String localName;
    private static String localIp;

    public static void init() throws UnknownHostException {
        inetAddress = InetAddress.getLocalHost();
        localName = inetAddress.getHostName();
        localIp = inetAddress.getHostAddress();
    }

    public static String getLocalIp() {
        return localIp;
    }

    public static String getLocalName() {
        return localName;
    }

    public static void sendMessage(MessageType messageType,
                                   String srcIp,
                                   String destIp) {
        LocalGameController.requestLogMessages("已经向服务器发送准备请求！");
    }
}
