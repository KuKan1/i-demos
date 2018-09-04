package com.registry;

import org.I0Itec.zkclient.ZkClient;

import java.util.List;

/**
 * Created by Admin on 2018/9/4.
 */
public class ZookeeperClient {

    public static void main(String[] args) {
        ZkClient zkClient = new ZkClient("47.93.40.16:2181");
        List<String> children = zkClient.getChildren("/xiaomai/server");
        if (children != null && !children.isEmpty()){
            for (String str:children){
                System.out.println(str);
            }
        }
    }
}
