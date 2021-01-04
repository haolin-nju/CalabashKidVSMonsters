package main.java.nju.linhao.utils;

import main.java.nju.linhao.enums.LogType;
import main.java.nju.linhao.enums.MessageType;
import main.java.nju.linhao.enums.Player;

import java.io.File;
import java.io.Serializable;

public class Log implements Serializable {
    private static final long serialVersionUID = 3103317843148814898L;
    private Player player;
    private LogType logType;
    private Object logContent;
    private long timeMilliseconds;

    public Log(Player player, LogType logType, Object logContent){
        this.player = player;
        this.logType = logType;
        this.logContent = logContent;
    }

    public Player getPlayer() {
        return player;
    }

    public LogType getLogType() {
        return logType;
    }

    public Object getLogContent() {
        return logContent;
    }

    public long getTimeMilliseconds(){
        return timeMilliseconds;
    }

    public void setTimeMilliseconds(long timeMilliseconds){
        this.timeMilliseconds = timeMilliseconds;
    }

    public void setLogContent(Object logContent) {
        this.logContent = logContent;
    }

    public void setLogType(LogType logType) {
        this.logType = logType;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public String toString(){
        return player + ": " + logType + "-" + logContent + "-" + timeMilliseconds;
    }
}
