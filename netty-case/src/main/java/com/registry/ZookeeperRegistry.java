package com.registry;

import org.I0Itec.zkclient.ZkClient;

/**
 * Created by Admin on 2018/9/4.
 */
public class ZookeeperRegistry implements Registry {

    private final String hostAddr;

    private ZkClient zkClient;

    public ZookeeperRegistry(String hostAddr) {
        this.hostAddr = hostAddr;
        initZkClient();
    }

    private void initZkClient() {
        zkClient = new ZkClient(hostAddr);
        System.out.println("创建客户端成功");
    }

    public void register(String parentPath,String path) {
        if (!zkClient.exists(parentPath)){
            zkClient.createPersistent(parentPath,true);
        }
        zkClient.createEphemeral(parentPath+"/"+path);
    }

    public void unregister(String path) {
        zkClient.delete(path);
    }
}
