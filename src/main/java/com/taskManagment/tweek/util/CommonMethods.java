package com.taskManagment.tweek.util;

import java.util.UUID;

public class CommonMethods {
    public static String getConanicalId(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
