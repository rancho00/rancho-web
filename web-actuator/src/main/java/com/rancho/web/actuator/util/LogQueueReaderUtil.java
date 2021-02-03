package com.rancho.web.actuator.util;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class LogQueueReaderUtil {

    // 先进先出的 阻塞队列
    private static BlockingQueue query = new LinkedBlockingQueue<>(10000);

    // 上次文件大小
    private static long lastTimeFileSize;

    public static void seekFileSize(long indexSize){
        LogQueueReaderUtil.lastTimeFileSize=indexSize;
    }

    @SuppressWarnings("unchecked")
    public static void run(String logUrl) {
        File logFile = new File(logUrl);
        try {
            long len = logFile.length();
            if (len < lastTimeFileSize) {
                lastTimeFileSize = len;
            } else if (len > lastTimeFileSize) {
                RandomAccessFile randomFile = new RandomAccessFile(logFile, "r");
                randomFile.seek(lastTimeFileSize);
                String tmp = null;

                while ((tmp = randomFile.readLine()) != null) {
                    query.add(new String(tmp.getBytes("ISO-8859-1"), "UTF-8"));

                }
                lastTimeFileSize = randomFile.length();
                randomFile.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String poll(String logUrl) {
        try {
            run(logUrl);
            String msg="";
            if(!query.isEmpty()){
               msg = (String) query.take();
            }
            return msg;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
