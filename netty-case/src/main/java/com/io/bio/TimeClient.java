package com.io.bio;

import java.io.*;
import java.net.Socket;

/**
 * Created by Admin on 2018/8/3.
 */
public class TimeClient {
    public static void main(String[] args) throws IOException, InterruptedException {
        int port = 8080;
        if (args != null && args.length>0){
            port = Integer.valueOf(args[0]);
        }
        Socket socket = null;
        BufferedReader in = null;
        PrintWriter out = null;

        try {
            socket = new Socket("127.0.0.1",port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);
            System.out.println("客户端发送请求");
            out.println("开始计时器");
            while (true){
                String msg = in.readLine();
                System.out.println(msg);
                if (msg == null){
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
//            in.close();
//            out.close();
        }
    }
}
