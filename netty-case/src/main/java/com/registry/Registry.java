package com.registry;

/**
 * Created by Admin on 2018/9/4.
 */
public interface Registry {

    void register(String parentPath,String path);

    void unregister(String path);
}
