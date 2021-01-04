package main.java.nju.linhao.io;

import main.java.nju.linhao.enums.LogType;
import main.java.nju.linhao.utils.Log;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.concurrent.locks.ReentrantLock;

// 注意：所有记录只有服务器负责保存！
public class Recorder {
    private LinkedList<Log> logs;
    private ReentrantLock logsLock;

    private File logFile;
    private String logDirectory;

    private long timeMilliseconds;
    private ReentrantLock timeMillisecondsLock;

    ObjectOutputStream out = null;

    private static Recorder recorder = new Recorder();

    private Recorder(){
    }

    public static Recorder getInstance(){
        return recorder;
    }

    public void init(String path) {
        this.logs = new LinkedList<>();
        this.logsLock = new ReentrantLock();
        this.logDirectory = path;
        timeMilliseconds = 0;
        timeMillisecondsLock = new ReentrantLock();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        this.logFile = new File(this.logDirectory, simpleDateFormat.format(new Date()) + ".txt");
        try {
            if (!logFile.getParentFile().exists()) {
                logFile.getParentFile().mkdir();
            }
            if (!logFile.exists()) {
                logFile.createNewFile();
            }
            out = new ObjectOutputStream(new FileOutputStream(logFile, true));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void recordLog(Log log) {
        timeMillisecondsLock.lock();
        try {
            if (timeMilliseconds == 0) {
                timeMilliseconds = System.currentTimeMillis();
            } else {
                long currentTimeMilliseconds = System.currentTimeMillis();
                log.setTimeMilliseconds(currentTimeMilliseconds - timeMilliseconds);
                timeMilliseconds = currentTimeMilliseconds;
            }
        } finally {
            timeMillisecondsLock.unlock();
        }
        try {
            synchronized (out) {
                out.writeObject(log);
                out.flush();
                if (log.getLogType() == LogType.SOMEONE_LOSE) {
                    out.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

//    public void recordLog(Log log) {
//        timeMillisecondsLock.lock();
//        try {
//            if (timeMilliseconds == 0) {
//                timeMilliseconds = System.currentTimeMillis();
//            } else {
//                long currentTimeMilliseconds = System.currentTimeMillis();
//                log.setTimeMilliseconds(currentTimeMilliseconds - timeMilliseconds);
//                timeMilliseconds = currentTimeMilliseconds;
//            }
//        } finally {
//            timeMillisecondsLock.unlock();
//        }
//        logsLock.lock();
//        try{
//            logs.add(log);
//        } finally{
//            logsLock.unlock();
//        }
//    }
//
//    public void writeLog() {
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
//        this.logFile = new File(this.logDirectory, simpleDateFormat.format(new Date()) + ".txt");
//        try{
//            if(!logFile.getParentFile().exists()){
//                logFile.getParentFile().mkdir();
//            }
//            if(!logFile.exists()){
//                logFile.createNewFile();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        logsLock.lock();
//        try {
//            out = new ObjectOutputStream(new FileOutputStream(logFile, true));
//            Iterator<Log> iterator = logs.iterator();
//            while(iterator.hasNext()){
//                Log log = iterator.next();
//                out.writeObject(log);
//                out.flush();
//                iterator.remove();
//            }
//        } catch(IOException e) {
//            e.printStackTrace();
//        }finally{
//            logsLock.unlock();
//            if(out!=null) {
//                try {
//                    out.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//}
