package main.java.nju.linhao.controller;

import main.java.nju.linhao.Main;

import java.io.*;
import java.net.Socket;

public class SocketController implements Runnable {
    private Socket socket;
    private int socketID;
    static int id = 0;


    public SocketController(){
        socket = null;
        socketID = id;
        id++;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public Socket getSocket() {
        return socket;
    }


    @Override
    public void run() {

        while(true){
            //System.out.println(socketID);
            //System.out.println(getSocket());
            try {
                if(getSocket()==null){
                    continue;
                }
                InputStream inputStream = socket.getInputStream();
                DataInputStream in = new DataInputStream(inputStream);
                String inStr = in.readUTF();
                System.out.println(inStr);
                String[] token = inStr.split("#");
                int playerID = Integer.parseInt(token[1].split("_")[1]);
                String outStr = "";
                switch (token[0]){
                    case "CLIENT1_READY":

                        synchronized (Main.map){
                            if(Main.map[playerID-1]==-1){
                                Main.map[playerID-1]=socketID;
                                outStr = "SUCCESS";
                            }else{
                                if(playerID==1){
                                    playerID=2;
                                }else{
                                    playerID=1;
                                }
                                Main.map[playerID-1]=socketID;
                                outStr = "FAILED";
                            }
                        }
                    // TODO: 其它case的处理
                }
                OutputStream outputStream = socket.getOutputStream();
                DataOutputStream out = new DataOutputStream(outputStream);
                out.writeUTF(outStr);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
