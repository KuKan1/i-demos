package com.io.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Admin on 2018/8/3.
 */
public class TimeServer {

    public static void main(String[] args) {
        Integer port = 8080;
        if (args!=null && args.length>0){
            try {
                port = Integer.parseInt(args[0]);
            }catch (Exception e){
            }
        }
        ServerSocket serverSocket = null;
        try {
           serverSocket  = new ServerSocket(port);
           while (true){
               Socket s = serverSocket.accept();
               System.out.println("服务端接收连接");
               new Thread(new TimeServerHandler(s)).start();
           }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (serverSocket != null){
                try {
                    serverSocket.close();
                } catch (IOException e) {
                }
            }
        }
    }
}
