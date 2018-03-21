package com.zhh.utils;

import java.util.UUID;

public class UUIDUtil {

    public static String genUUID(){
        return UUID.randomUUID().toString().replace("-","").substring(0,32);
    }

}
