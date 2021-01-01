package main.java.nju.linhao.utils;

import main.java.nju.linhao.enums.LogType;
import main.java.nju.linhao.enums.MessageType;

import java.io.File;
import java.io.Serializable;

public class Log implements Serializable {
    private LogType logType;
    private String logContent;

    public Log(LogType logType, String logContent){
        this.logType = logType;
        this.logContent = logContent;
    }

    public LogType getLogType() {
        return logType;
    }

    public String getLogContent() {
        return logContent;
    }
}
