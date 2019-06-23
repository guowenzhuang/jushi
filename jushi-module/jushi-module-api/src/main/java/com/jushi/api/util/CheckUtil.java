package com.jushi.api.util;

import com.jushi.api.exception.CheckException;


public class CheckUtil {
    public static void checkEmpty(String field,Object value){
        if (EmptyUtils.isEmpty(value)) {
            throw new CheckException(field,value,field+" 不能为null");
        }
    }
}
