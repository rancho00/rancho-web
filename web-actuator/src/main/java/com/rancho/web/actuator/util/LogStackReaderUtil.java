package com.rancho.web.actuator.util;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Stack;

public class LogStackReaderUtil {

    private static Stack stack=new Stack();

    public static void run(String logUrl,int numRead) {
        int count = 0;//定义行数
        File file = new File(logUrl);
        RandomAccessFile fileRead = null;
        try {
            fileRead = new RandomAccessFile(file, "r"); //用读模式
            long length = fileRead.length();//获得文件长度
            if (length == 0L) {//文件内容为空
                return;
            } else {
                // 开始位置
                long pos = length - 1;
                LogQueueReaderUtil.seekFileSize(length);
                while (pos > 0) {
                    pos--;
                    fileRead.seek(pos); // 开始读取
                    if (fileRead.readByte() == '\n') {//有换行符，则读取
                        String line = new String(fileRead.readLine().getBytes("ISO-8859-1"), "UTF-8");
                        stack.push(line);
                        count++;
                        if (count == numRead) {//满足指定行数 退出。
                            break;
                        }
                    }
                }
                if (pos == 0) {
                    fileRead.seek(0);
                    String line = new String(fileRead.readLine().getBytes("ISO-8859-1"), "UTF-8");
                    stack.push(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileRead != null) {
                try {
                    fileRead.close();
                } catch (Exception e) {
                }
            }
        }
    }

    public static String popNum(String logUrl, int numRead) {
        run(logUrl,numRead);
        StringBuilder sb=new StringBuilder();
        if(numRead>=stack.size()){
            numRead=stack.size();
        }
        for(int i=0;i<numRead;i++){
            sb.append((String)stack.pop());
        }
        return sb.toString();
    }
}
