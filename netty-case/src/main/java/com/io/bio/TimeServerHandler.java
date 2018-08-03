package com.io.bio;

import java.io.*;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Admin on 2018/8/3.
 */
public class TimeServerHandler implements Runnable {

    private Socket socket;

    public TimeServerHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        BufferedReader reader = null;
        PrintWriter writer = null;
        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);
            System.out.println("开始读取信息");
            String body = reader.readLine();
            System.out.println(body);
            final PrintWriter finalWriter = writer;
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                        finalWriter.println("Oh,Tim!");
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            },2000,2000);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
//            if (reader != null){
//                try {
//                    reader.close();
//                } catch (IOException e) {
//                }
//                reader = null;
//            }
//            if (writer != null){
//                writer.close();
//            }
//            writer = null;
//            if (socket !=null){
//                try {
//                    socket.close();
//                } catch (IOException e) {
//                }
//                socket = null;
//            }
        }
    }
}
