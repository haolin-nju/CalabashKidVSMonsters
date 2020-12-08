package main.java.nju.linhao.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class NetworkController{
    private static InetAddress inetAddress = null;
    private static String localName;
    private static String localIp;

    public static void init(){
        try {
            inetAddress = InetAddress.getLocalHost();
            localName = inetAddress.getHostName();
            localIp = inetAddress.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public static String getLocalIp(){
        return localIp;
    }

    public static String getLocalName(){
        return localName;
    }
}
