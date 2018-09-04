package com.kukan.listener;

import org.I0Itec.zkclient.IZkChildListener;

import java.util.List;

/**
 * Created by Admin on 2018/9/4.
 */
public class Listener implements IZkChildListener {

    @Override
    public void handleChildChange(String s, List<String> list) throws Exception {
        System.out.println("父节点路径为："+s);
        for (String node:list){
            System.out.println("变更的节点为:"+node);
        }
    }
}
