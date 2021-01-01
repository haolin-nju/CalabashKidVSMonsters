package main.java.nju.linhao.io;

import main.java.nju.linhao.utils.Configuration;
import main.java.nju.linhao.utils.Log;
import main.java.nju.linhao.utils.Message;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.locks.ReentrantLock;

// 注意：所有记录只有服务器负责保存！
public class Recorder {
    private File logFile;
    private String logDirectory;
    private static LinkedList<Log> logs;

    private ReentrantLock logsLock;

    public Recorder(){
        this(Configuration.DEFAULT_RECORD_DIR_PATH);
    }

    public Recorder(String path){
        this.logDirectory = path;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        this.logFile = new File(this.logDirectory, simpleDateFormat.format(new Date()) + ".txt");
        try{
            if(!logFile.getParentFile().exists()){
                logFile.getParentFile().mkdir();
            }
            if(!logFile.exists()){
                logFile.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        logsLock = new ReentrantLock();
        logs = new LinkedList<>();
    }

    public void recordLog(Log log) {
        logsLock.lock();
        try{
            logs.add(log);
        } finally {
            logsLock.unlock();
        }
    }

    public void recordLogs(LinkedList<Log> logLinkedList){
        logsLock.lock();
        try{
            logs.addAll(logLinkedList);
        } finally {
            logsLock.unlock();
        }
    }

    public void writeLog() {
        logsLock.lock();
        try {
            OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(logFile), "UTF-8");
            Iterator<Log> logIterator = logs.iterator();
            while (logIterator.hasNext()) {
                Log nextLog = logIterator.next();
                out.write(nextLog.toString());
                logIterator.remove();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            logsLock.unlock();
        }
    }
}
